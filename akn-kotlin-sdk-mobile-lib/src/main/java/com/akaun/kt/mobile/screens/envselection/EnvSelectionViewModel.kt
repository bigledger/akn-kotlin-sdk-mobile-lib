package com.akaun.kt.mobile.screens.envselection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import kotlinx.coroutines.launch

class EnvSelectionViewModel: ViewModel() {

    var isEnvSelected by mutableStateOf(false)
        private set
    fun setEnvironment(envUrl: String) {
        viewModelScope.launch {
            val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
            val editor = sharedPreferences.edit()
            editor.putString(CommonSharedPreferenceConstants.BASE_URL, envUrl)
            editor.apply()
            isEnvSelected = true
        }
    }
}