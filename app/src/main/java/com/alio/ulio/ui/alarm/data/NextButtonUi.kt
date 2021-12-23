package com.alio.ulio.ui.alarm.data

data class NextButtonUi(
    val isEnable: Boolean = true,
    val action: (() -> Unit)? = null
)
