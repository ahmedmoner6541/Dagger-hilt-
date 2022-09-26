package com.EcommerceApplication.data.local.shared

import android.content.Context
import android.content.SharedPreferences
import com.EcommerceApplication.util.Constants.PREFS_TOKEN_FILE
import com.EcommerceApplication.util.Constants.REFRESH_TOKENUSER_TOKEN
import com.EcommerceApplication.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager
@Inject constructor(
    @ApplicationContext context: Context
) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun refreshToken(token: String) {
        val editor = prefs.edit()
        editor.putString(REFRESH_TOKENUSER_TOKEN, token)
        editor.apply()
    }
    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun clearShared(){
        prefs.edit().clear()
    }
}