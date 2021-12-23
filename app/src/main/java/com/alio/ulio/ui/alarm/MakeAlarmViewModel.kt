package com.alio.ulio.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alio.ulio.ui.alarm.data.NextButtonUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.time.LocalDate

class MakeAlarmViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _progressImg = MutableLiveData<Int>()
    val progressImg: LiveData<Int> = _progressImg

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    private val _btnNextUi = MutableLiveData(NextButtonUi())
    val btnNextUi: LiveData<NextButtonUi> = _btnNextUi

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setTitle(input: String) {
        _title.value = input
    }

    fun setProgressImg(imgName: Int) {
        _progressImg.value = imgName
    }

    fun setBtnNextEnable(isEnable: Boolean) {
        _btnNextUi.value = btnNextUi.value?.copy(
            isEnable = isEnable
        )
    }

    fun setBtnNextAction(action: () -> Unit) {
        Timber.d("btnNextUi: ${btnNextUi.value}")
        _btnNextUi.value = btnNextUi.value?.copy(
            action = action
        )
    }

}