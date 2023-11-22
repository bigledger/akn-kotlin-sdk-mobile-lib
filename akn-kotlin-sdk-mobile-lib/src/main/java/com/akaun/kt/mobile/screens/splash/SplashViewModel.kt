package com.akaun.kt.mobile.screens.splash

import android.os.Build
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

        val authTokenMap = authToken?.let { decodeAuthToken(it) }

        val expTime = authTokenMap?.get("exp")

        if(expTime != null){
            val localDateTimeExpiry = convertUnixTimestampToLocalDateTime(expTime.toString())
            // TODO : CHANGE IT BACK
//            return LocalDateTime.now() >= localDateTimeExpiry
            return LocalDateTime.now().plusDays(35) >= localDateTimeExpiry
        }

        return false

    }
}