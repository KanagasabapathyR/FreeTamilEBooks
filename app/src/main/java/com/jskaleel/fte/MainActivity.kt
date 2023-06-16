package com.jskaleel.fte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jskaleel.fte.core.model.ThemeConfig
import com.jskaleel.fte.ui.components.FteAppState
import com.jskaleel.fte.ui.components.rememberFteAppState
import com.jskaleel.fte.ui.screens.app.FteApp
import com.jskaleel.fte.ui.theme.FteTheme
import com.jskaleel.fte.ui.theme.shouldUseDarkTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(
            MainActivityUiState()
        )
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition { uiState.isLoading }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val appState: FteAppState = rememberFteAppState()
            val darkTheme = shouldUseDarkTheme(
                isLoading = uiState.isLoading,
                darkThemeConfig = uiState.uiModel.themeConfig,
            )

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }
            FteTheme(
                darkTheme = darkTheme,
            ) {
                FteApp(
                    isOnline = uiState.isOnline,
                    appState = appState
                )
            }
        }
    }
}

data class MainActivityUiModel(
    val themeConfig: ThemeConfig = ThemeConfig.FOLLOW_SYSTEM,
)