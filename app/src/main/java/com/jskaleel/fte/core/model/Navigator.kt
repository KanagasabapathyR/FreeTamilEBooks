package com.jskaleel.fte.core.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

sealed class NavigationState<out T> {
    data class Navigate<T>(val data: T, var isConsumed: Boolean = false) : NavigationState<T>()
    object Idle : NavigationState<Nothing>()
}

fun <T> mutableNavStateOf() = mutableStateOf<NavigationState<T>>(NavigationState.Idle)

@Composable
inline fun <T> NavigationState<T>.listen(crossinline block: (T) -> Unit) {
    LaunchedEffect(key1 = this) {
        when (this@listen) {
            is NavigationState.Navigate -> {
                if (!isConsumed) {
                    isConsumed = true
                    block(data)
                }
            }
            NavigationState.Idle -> {}
        }
    }
}

inline fun <reified T> navigate(data: T) = NavigationState.Navigate(data)


