package com.alio.ulio.ui.alarm.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alio.ulio.domain.AlarmUseCase
import com.alio.ulio.domain.UploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordAlarmViewModel @Inject constructor(
    private val alarmUseCase: AlarmUseCase,
    private val uploadUseCase: UploadUseCase
) :
    ViewModel() {

    private val _uploadUrl = MutableLiveData<String>()
    val uploadUrl: LiveData<String> = _uploadUrl


    fun findUploadUrl(fileName: String) {
        viewModelScope.launch {
            alarmUseCase.invoke(fileName).collect { url ->
                _uploadUrl.value = url.uploadURL
            }
        }
    }

    fun upload(file: RequestBody) {
        viewModelScope.launch {
            uploadUseCase.invoke(uploadUrl.value ?: "", file).collect { result ->
                Timber.d("result: $result")
            }
        }
    }
}