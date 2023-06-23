package com.jskaleel.fte.domain.usecase

import com.jskaleel.fte.core.model.toImage
import com.jskaleel.fte.data.repository.BooksRepository
import com.jskaleel.fte.domain.model.EBook
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository
) : HomeUseCase {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getBooks(): Flow<List<EBook>> {
        return booksRepository.getBooks()
            .distinctUntilChanged()
            .flatMapLatest { localBooks ->
                if (localBooks.isNotEmpty()) {
                    val books = localBooks.map { book ->
                        EBook(
                            bookid = book.bookid,
                            title = book.title,
                            author = book.author,
                            image = book.image.toImage(),
                            epub = book.epub,
                            category = book.category,
                        )
                    }
                    flowOf(books)
                } else {
                    flowOf(emptyList())
                }
            }
    }
}
