package com.alio.ulio.ui.alarm.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alio.ulio.domain.AlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordAlarmViewModel @Inject constructor(private val alarmUseCase: AlarmUseCase) :
    ViewModel() {

    var uploadUrl: String = ""


    fun findUploadUrl(fileName: String) {
        viewModelScope.launch {
            alarmUseCase.invoke(fileName).collect { url ->
                uploadUrl = url.uploadURL
                Timber.d("uploadUrl : $uploadUrl")
            }
        }
    }
}