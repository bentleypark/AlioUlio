package com.alio.ulio.ui.alarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class MakeAlarmViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _progressAnim = MutableStateFlow("")
    val progressAnim: StateFlow<String> = _progressAnim

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setTitle(input: String) {
        _title.value = input
    }

    fun setProgressAnim(name: String) {
        _progressAnim.value = name
    }

}