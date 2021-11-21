package com.alio.ulio.ui.alarm

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AlarmTimeSelectionUi() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    Scaffold(
        topBar = { AlarmUiToolbar(true, "알람 조건을 \n설정하세요", 2) }) {

    }
}