package com.alio.ulio.ui.alarm

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AlarmNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AlarmScreen.AlarmTypeSelectionScreen.router
    ) {
        composable(route = AlarmScreen.AlarmTypeSelectionScreen.router) {
            AlarmTypeSelectionUi(navController)
        }
        composable(
            route = AlarmScreen.AlarmTimeSelectionScreen.router,
            arguments = listOf(navArgument("alarmType") {
                type = NavType.StringType
                defaultValue = "onetime"
                nullable = false
            })
        ) {
            AlarmTimeSelectionUi(navController)
        }
    }
}

sealed class AlarmScreen(val router: String) {
    object AlarmTypeSelectionScreen : AlarmScreen("alarm_type_selection_screen")
    object AlarmTimeSelectionScreen : AlarmScreen("alarm_time_screen")
}