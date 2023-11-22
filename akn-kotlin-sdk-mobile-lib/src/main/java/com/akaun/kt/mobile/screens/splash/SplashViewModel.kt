package com.akaun.kt.mobile.screens.splash

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.mobile.utils.convertUnixTimestampToLocalDateTime
import com.akaun.kt.mobile.utils.decodeAuthToken
import java.time.LocalDateTime

class SplashViewModel: ViewModel() {

    fun isSignedIn(): Boolean {
        val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)

        return sharedPreferences.getBoolean(
            CommonSharedPreferenceConstants.IS_USER_SIGNED_IN,
            false
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isAuthTokenExpired() : Boolean {
        val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        val authToken =  sharedPreferences.getString(CommonSharedPreferenceConstants.AUTH_TOKEN,null)

        Log.d("authToken", "isAuthTokenExpired: ${authToken}")
        val authTokenMap = authToken?.let { decodeAuthToken(it) }

        val expTime = authTokenMap?.get("exp")

        Log.d("expTime", "isAuthTokenExpired: ${expTime.toString()} ")

        if(expTime != null){
            val localDateTimeExpiry = convertUnixTimestampToLocalDateTime(expTime.toString())
            // TODO : CHANGE IT BACK
//            return LocalDateTime.now() >= localDateTimeExpiry
            Log.d("time", "isAuthTokenExpired: ${LocalDateTime.now().plusDays(35)}")
            Log.d("localDateTImeExpiy", "isAuthTokenExpired: ${localDateTimeExpiry} ")
            return LocalDateTime.now().plusDays(35) >= localDateTimeExpiry
        }

        return false

    }
}