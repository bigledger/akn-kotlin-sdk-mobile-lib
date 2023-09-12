package com.akaun.kt.mobile.screens.splash

import androidx.lifecycle.ViewModel
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants

class SplashViewModel: ViewModel() {

    fun isSignedIn(): Boolean {
        val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)

        return sharedPreferences.getBoolean(
            CommonSharedPreferenceConstants.IS_USER_SIGNED_IN,
            false
        )
    }
}