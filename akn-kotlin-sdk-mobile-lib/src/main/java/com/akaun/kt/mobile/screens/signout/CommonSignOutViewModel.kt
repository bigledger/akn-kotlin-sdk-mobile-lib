package com.akaun.kt.mobile.screens.signout

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper

abstract class CommonSignOutViewModel: ViewModel() {
    var isLoading by mutableStateOf(true)
    fun clearCommonSharedPreferences() {
        val loginSp = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        val loginSpEditor = loginSp.edit()
        loginSpEditor.clear()
        loginSpEditor.apply()

        val commonSp = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
        val commonSpEditor = commonSp.edit()
        commonSpEditor.clear()
        commonSpEditor.apply()
    }

    abstract fun clearAppSharedPreferences(): Unit

    abstract fun onSignOut(): Unit
}