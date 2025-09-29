package com.akaun.kt.mobile.screens.login
import com.akaun.kt.mobile.di.LoginModule
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.sdk.models.aggregates.erp.UserAppletLinkResponse
import com.akaun.kt.sdk.models.dbschema.GoogleLoginRequest
import com.akaun.kt.sdk.models.dbschema.LoginRequest
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginScreenViewModel: ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
        private set

    var isInvalid by mutableStateOf(false)
        private set

    fun resetIsError() {
        isError = false
    }

    fun resetIsInvalid() {
        isInvalid = false
    }

    fun signInWithEmailOrMobileWithPassword(
        emailOrMobileNumber: String,
        password: String,
        appletCode: String,
        onLoginSuccess: () -> Unit
    ) = viewModelScope.launch {
        isLoading = true
        try {
            val loginRequest = if (isValidEmail(emailOrMobileNumber)) {
                LoginRequest(email = emailOrMobileNumber, password = password)
            } else {
                LoginRequest(mobileNumber = emailOrMobileNumber, password = password)
            }

            val loginResult = LoginModule.provideLoginClient().login(loginRequest)
            val userAppletLinks = LoginModule.provideUserAppletLinkClient(
                loginResult.data?.authToken ?: "").getUserAppletLInks()

            if (userAppletLinks.data == null && loginResult.data == null) {
                isError = true
                isLoading = false
            } else {
                loginResult.data?.let {
                    setupLoginSharedPreferences(
                        appletCode = appletCode,
                        loginResponseBody = it,
                        userAppletLinkResponse = userAppletLinks.data ?: emptyList()
                    )
                }

                // Proceed to main page
                onLoginSuccess()
            }
        } catch (ex: Exception) {
            isError = true
            isLoading = false
            Log.d("Failed", "Failed Reason: ${ex.message}")
        }
    }

    fun signInWithGoogle(
        googleToken: String,
        googleClientId: String,
        appletCode: String,
        onLoginSuccess: () -> Unit
    ) = viewModelScope.launch {
        isLoading = true
        try {
            val googleLoginRequest = GoogleLoginRequest(
                googleToken = googleToken,
                googleAppId = googleClientId
            )

            val loginResult = LoginModule.provideLoginClient().loginToGoogle(googleLoginRequest)
            val userAppletLinks = LoginModule.provideUserAppletLinkClient(
                loginResult.data?.authToken ?: "").getUserAppletLInks()

            if (userAppletLinks.data == null && loginResult.data == null) {
                isError = true
                isLoading = false
            } else {
                loginResult.data?.let {
                    setupLoginSharedPreferences(
                        appletCode = appletCode,
                        loginResponseBody = it,
                        userAppletLinkResponse = userAppletLinks.data ?: emptyList()
                    )
                }
                // Proceed to main page
                onLoginSuccess()
            }
        } catch (ex: Exception) {
            isError = true
            isLoading = false
            Log.d("Failed", "Failed Reason: ${ex.message}")
        }
    }

    private fun setupLoginSharedPreferences(
        appletCode: String,
        loginResponseBody: LoginResponse,
        userAppletLinkResponse: List<UserAppletLinkResponse>
    ) {
        val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        val editor = sharedPreferences.edit()
        // auth token
        editor.putString(CommonSharedPreferenceConstants.AUTH_TOKEN, loginResponseBody.authToken)

        // app login subject guid
        editor.putString(CommonSharedPreferenceConstants.SUBJECT_GUID,
            loginResponseBody.subjectGuid
        )
        editor.apply()

        // Indicate user has signed in
        editor.putBoolean(CommonSharedPreferenceConstants.IS_USER_SIGNED_IN, true)

        // Indicate user has just signed in
        editor.putBoolean(CommonSharedPreferenceConstants.JUST_SIGNED_IN, true)

        // Filter based on the appletCode, to only get links for a particular applet
        val userAppletLinkList = userAppletLinkResponse.filter {
            it.applet.applet_code.lowercase() == appletCode.lowercase()
        }

        // List of tenants found with the appletCode from filter above
        val tenantCodesList = userAppletLinkList.map { it.tenant.tenant_code }
        val tenantGuidList = userAppletLinkList.map { it.tenant.guid }

        // Get the first applet guid
        val firstAppletGuid: String? = userAppletLinkList.firstOrNull()?.applet?.guid

        // Store tenantCode in sharedPreferences
        if (tenantCodesList.isNotEmpty()) {
            val serializedTenantCodes = tenantCodesList.joinToString(",")
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_LIST, serializedTenantCodes)
            editor.apply()

            // Put the first tenantCode as default selected in sharedPreferences
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED,
                tenantCodesList[0]
            )
            editor.apply()

            val serializedTenantGuids = tenantGuidList?.joinToString(",")
            editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_LIST, serializedTenantGuids)
            editor.apply()

            editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_SELECTED,
                tenantGuidList[0]
            )
            editor.apply()

            // appletGuid
            editor.putString(CommonSharedPreferenceConstants.APPLET_GUID, firstAppletGuid)
            editor.apply()

        } else {
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_LIST,null)
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED,null)
            editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_LIST,null)
            editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_SELECTED,null)
            editor.putString(CommonSharedPreferenceConstants.APPLET_GUID, null)
            editor.apply()
        }
    }
}