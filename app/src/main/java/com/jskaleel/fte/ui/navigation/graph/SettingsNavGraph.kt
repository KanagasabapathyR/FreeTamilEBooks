package com.jskaleel.fte.ui.navigation.graph

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jskaleel.fte.ui.navigation.Route
import com.jskaleel.fte.ui.navigation.Screen

fun NavGraphBuilder.settingsNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.Settings.route,
        route = Route.Settings.name
    ) {
        composable(route = Screen.Settings.route) {
            Text(text = "Settings Screen", style = MaterialTheme.typography.headlineSmall)
        }
    }
}