package com.epubreader.android

import android.content.Context
import com.epubreader.android.utils.ReaderResult

interface Reader {
    suspend fun download(url: String): ReaderResult<Long>
    suspend fun openBook(bookId: Long, context: Context)
    suspend fun deleteBook(bookId: Long)
}