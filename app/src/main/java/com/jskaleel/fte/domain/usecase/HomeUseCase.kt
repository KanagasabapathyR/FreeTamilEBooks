package com.jskaleel.fte.domain.usecase

import com.jskaleel.fte.domain.model.EBook
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getBooks(): Flow<List<EBook>>
}