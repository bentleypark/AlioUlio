package com.alio.ulio.ui.alarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MakeAlarmViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _progressAnim = MutableStateFlow("")
    val progressAnim: StateFlow<String> = _progressAnim

    fun setTitle(input: String) {
        _title.value = input
    }

    fun setProgressAnim(name: String) {
        _progressAnim.value = name
    }

}