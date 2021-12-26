package com.alio.ulio.data.model

import com.google.gson.annotations.SerializedName

data class FindUploadUrlResponse(
    @SerializedName("uploadURL")
    val uploadURL: String)
