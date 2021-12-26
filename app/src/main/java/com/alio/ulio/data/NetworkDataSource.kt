package com.alio.ulio.data

import com.alio.ulio.data.model.FindUploadUrlResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun findUploadUrl(fileName: String): Flow<DataState<FindUploadUrlResponse>> =
        callbackFlow {
            trySend(DataState.Success(apiService.findUploadUrl(fileName)))
            awaitClose { close() }
        }
}