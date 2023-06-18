package com.jskaleel.fte.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jskaleel.fte.R
import com.jskaleel.fte.core.model.ImageType
import com.jskaleel.fte.domain.model.EBook
import com.jskaleel.fte.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState())

    val uiState = viewModelState.map {
        it.toUiState()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value.toUiState()
    )

    init {
        viewModelState.update { it.copy(loading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.getBooks().collect { books ->
                if (books.isNotEmpty()) {
                    viewModelState.update {
                        it.copy(
                            loading = false,
                            books = books
                        )
                    }
                }
            }
        }
    }
}

private data class HomeViewModelState(
    val loading: Boolean = false,
    val books: List<EBook> = emptyList(),
) {
    fun toUiState() = HomeViewModelUiState(
        loading = loading,
        books = books.map {
            BooksUiModel(
                title = it.title,
                author = it.author,
                category = it.category,
                image = it.image,
                icon = ImageType.ResourceImage(R.drawable.ic_download)
            )
        },
    )
}

data class HomeViewModelUiState(
    val loading: Boolean,
    val books: List<BooksUiModel>,
)