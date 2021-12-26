package com.alio.ulio.data

import com.alio.ulio.data.model.FindUploadUrlResponse
import com.alio.ulio.data.model.UploadAudioFileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL = "https://dst0smadj7.execute-api.ap-northeast-2.amazonaws.com/dev/"
    }

    @FormUrlEncoded
    @POST("voices")
    suspend fun findUploadUrl(@Field("name") email: String): FindUploadUrlResponse

    //    @Multipart
    @Headers("Content-Type: audio/mpeg")
    @PUT
    suspend fun uploadAudioFile(
        @Url url: String,
        @Body file: RequestBody,
    ): UploadAudioFileResponse

}