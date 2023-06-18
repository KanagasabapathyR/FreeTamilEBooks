package com.jskaleel.fte.data.repository

import com.jskaleel.fte.data.source.local.entities.ELocalBooks
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooks(): Flow<List<ELocalBooks>>
    suspend fun refreshBooks()
}