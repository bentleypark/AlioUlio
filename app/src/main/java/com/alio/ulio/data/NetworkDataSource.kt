package com.alio.ulio.data

import com.alio.ulio.data.model.FindUploadUrlResponse
import com.alio.ulio.data.model.UploadAudioFileResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun findUploadUrl(fileName: String): Flow<DataState<FindUploadUrlResponse>> =
        callbackFlow {
            trySend(DataState.Success(apiService.findUploadUrl(fileName)))
            awaitClose { close() }
        }


    suspend fun uploadAudioFile(
        url: String,
        file: RequestBody
    ): Flow<DataState<UploadAudioFileResponse>> =
        callbackFlow {
            trySend(DataState.Success(apiService.uploadAudioFile(url = url, file = file)))
            awaitClose { close() }
        }
}