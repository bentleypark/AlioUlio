package com.alio.ulio.ui.alarm

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AlarmTimeSelectionUi(navController: NavController) {
    Scaffold(
        topBar = { AlarmUiToolbar(true, "알람 조건을 \n설정하세요", 2) }) {

    }
}