package com.jskaleel.fte.data.repository

import com.jskaleel.fte.core.extensions.lastSyncBefore
import com.jskaleel.fte.data.source.datastore.AppPreferenceStore
import com.jskaleel.fte.data.source.local.dao.LocalBooksDao
import com.jskaleel.fte.data.source.local.dao.SavedBooksDao
import com.jskaleel.fte.data.source.local.entities.ELocalBooks
import com.jskaleel.fte.data.source.remote.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class BooksRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localBooks: LocalBooksDao,
    private val savedBooks: SavedBooksDao,
    private val appPreferenceStore: AppPreferenceStore
) : BooksRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getBooks(): Flow<List<ELocalBooks>> {
        var lastSync = runBlocking { appPreferenceStore.getLastSync() }.lastSyncBefore()
        return localBooks
            .getBooks()
            .distinctUntilChanged()
            .flatMapLatest { books ->
                Timber.tag("Khaleel").d("getBooks books: ${books.size} lastSync : $lastSync")
                return@flatMapLatest if (books.isEmpty() || lastSync == -1L || lastSync > 1) {
                    refreshBooks()
                    lastSync = appPreferenceStore.getLastSync()
                    flowOf(emptyList())
                } else {
                    flowOf(books)
                }
            }
    }

    override suspend fun refreshBooks() {
        appPreferenceStore.setLastSync()
        val response = apiService.getBooks()
        if (response.code() == HttpsURLConnection.HTTP_OK) {
            response.body()?.books?.map {
                it.toEntityModel()
            }?.also {
                localBooks.deleteAll()
                localBooks.insert(it)
            }
        }
    }
}