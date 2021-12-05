package com.alio.ulio.ui.alarm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AlarmDateTimeSelectionViewModel @Inject constructor() : ViewModel() {

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }
}