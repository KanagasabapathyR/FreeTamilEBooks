package com.epubreader.android

import android.content.Context
import com.epubreader.android.utils.ReaderResult

class ReaderImpl(private val readerConfig: ReaderConfig) : Reader {
    override suspend fun download(url: String): ReaderResult<Long> {
        return readerConfig.importBookFromUrl(url = url)
    }

    override suspend fun openBook(bookId: Long, context: Context) {
        readerConfig.openBook(bookId = bookId, context = context)
    }

    override suspend fun deleteBook(bookId: Long) {

    }
}