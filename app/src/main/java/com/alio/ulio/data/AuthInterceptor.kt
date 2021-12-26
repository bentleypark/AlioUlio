package com.alio.ulio.data

import com.alio.ulio.utils.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPreferenceManager: SharedPreferenceManager) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sharedPreferenceManager.getAccessToken().let {
            requestBuilder.addHeader("token", "$it")
        }
        return chain.proceed(requestBuilder.build())
    }
}