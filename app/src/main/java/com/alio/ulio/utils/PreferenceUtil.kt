package com.alio.ulio.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

object PreferenceUtil {
    fun getPref(context: Context) =
        context.getSharedPreferences(PreferenceUtil.javaClass.name, Context.MODE_PRIVATE)
}

class SharedPreferenceManager @Inject constructor(
    private val pref: SharedPreferences
) {
    fun getAccessToken() = pref.getString(TOKEN, "")

    fun setAccessToken(token: String) {
        pref.edit { putString(TOKEN, token) }
    }

    companion object {
        const val TOKEN = "token"
    }
}
