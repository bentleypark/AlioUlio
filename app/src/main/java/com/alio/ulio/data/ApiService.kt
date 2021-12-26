package com.alio.ulio.data

import com.alio.ulio.data.model.FindUploadUrlResponse
import com.alio.ulio.data.model.UploadAudioFileResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL = "https://dst0smadj7.execute-api.ap-northeast-2.amazonaws.com/dev/"
    }

    @FormUrlEncoded
    @POST("voices")
    suspend fun findUploadUrl(@Field("name") email: String): FindUploadUrlResponse

    @Multipart
    @POST
    suspend fun uploadAudioFile(
        @Header("Content-Type") contentType: String = "audio/mpeg",
        @Url url: String,
        @Part file: MultipartBody.Part,
    ): UploadAudioFileResponse

}