package com.alio.ulio.ui.alarm.alarm_condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegularAlarmDateTimeSelectionViewModel : ViewModel() {

    private val _selectedAlarmDay = MutableLiveData<List<AlarmDayUi>>()
    val selectedAlarmDay: LiveData<List<AlarmDayUi>> = _selectedAlarmDay

    fun updateAlarmDayList(newAlarmDayUi: AlarmDayUi) {
        val currentList = selectedAlarmDay.value ?: emptyList()

        _selectedAlarmDay.value = currentList.toMutableList().apply {
            if (contains(newAlarmDayUi)) {
                remove(newAlarmDayUi)
            } else {
                add(newAlarmDayUi)
            }
        }
    }

    fun convertDayOfWeek(): String {
        val sb = java.lang.StringBuilder()
        val origin = selectedAlarmDay.value?.map { it.alarmDay.dayOfWeek }?.sorted()
        if (!origin.isNullOrEmpty()) {
            for (i in origin.indices) {
                if (i > 0 && i < origin.size)
                    sb.append(", ")
                sb.append(origin[i].toDayLabel())
            }
        }
        return sb.toString()
    }

    private fun Int.toDayLabel(): String {
        return when (this) {
            1 -> "월"
            2 -> "화"
            3 -> "수"
            4 -> "목"
            5 -> "금"
            6 -> "토"
            7 -> "일"
            else -> ""
        }
    }
}