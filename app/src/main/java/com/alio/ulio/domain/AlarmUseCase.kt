package com.alio.ulio.domain

import com.alio.ulio.data.DataState
import com.alio.ulio.data.NetworkDataSource
import com.alio.ulio.data.model.FindUploadUrlResponse
import com.alio.ulio.data.model.UploadAudioFileResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

@ExperimentalCoroutinesApi
class AlarmUseCase(private val networkDataSource: NetworkDataSource) {

    operator fun invoke(fileName: String): Flow<FindUploadUrlResponse> =
        callbackFlow {
            networkDataSource.findUploadUrl(fileName)
                .catch { exception -> Timber.e(exception) }
                .collect {
                    when (it) {
                        is DataState.Success -> {
                            trySend(it.data)
                        }
                        is DataState.Error -> {
                            Timber.e("DataState.Error")
                        }
                    }
                }
        }
}

@ExperimentalCoroutinesApi
class UploadUseCase(private val networkDataSource: NetworkDataSource) {


    operator fun invoke(url: String, file: RequestBody): Flow<Unit> =
        callbackFlow {
            networkDataSource.uploadAudioFile(url, file)
                .catch { exception -> Timber.e(exception) }
                .collect {
                    when (it) {
                        is DataState.Success -> {
                            trySend(it.data)
                        }
                        is DataState.Error -> {
                            Timber.e("DataState.Error")
                        }
                    }
                }
        }
}