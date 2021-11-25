package com.alio.ulio.ui.alarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MakeAlarmViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val tilte: StateFlow<String> = _title

    fun setTitle(input: String) {
        _title.value = input
    }
}