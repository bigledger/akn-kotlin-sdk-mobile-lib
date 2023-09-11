package com.akaun.kt.mobile.screens.login
import android.annotation.SuppressLint
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
        onLoginSuccess: () -> Unit,
        appletCode: String
    ) = viewModelScope.launch {
        isLoading = true
        //TODO: Use the common shared preferences constants
        try {
            val loginRequest = if (isValidEmail(emailOrMobileNumber)) {
                LoginRequest(email = emailOrMobileNumber, password = password)
            } else {
                LoginRequest(mobileNumber = emailOrMobileNumber, password = password)
            }
            val loginAPI = LoginModule.provideLoginClient()
            val call = loginAPI.loginWithCall(loginRequest)
            call.enqueue(object : Callback<BasicApiResponseModel<LoginResponse>> {
                @SuppressLint("CommitPrefEdits")
                override fun onResponse(call: Call<BasicApiResponseModel<LoginResponse>>, response: Response<BasicApiResponseModel<LoginResponse>>) {
                    if (response.isSuccessful) {
                        val responseBody: BasicApiResponseModel<LoginResponse>? = response.body()
                        if (responseBody != null) {

                            setupLoginSharedPreferences(responseBody = responseBody, appletCode = appletCode)
                            // Proceed to main page
                            onLoginSuccess()
                        }
                    } else {
                        isLoading = false
                        isInvalid = true
                        val errorBody: String? = response.errorBody()?.string()
                        // Handle error response
                        Log.d("Error response", "error reason: $errorBody")
                    }
                }

                // TODO: Handle onFailure
                override fun onFailure(call : Call<BasicApiResponseModel<LoginResponse>>, t: Throwable) {
                    isLoading = false
                    isError = true
                    // Handle failure
                    Log.e("API Call", "Failed Reason: ${t.message}")
                }

            })
        } catch (ex: Exception) {
            isError = true
            isLoading = false
            Log.d("Failed", "Failed Reason: ${ex.message}")
        }


    }

    fun setupLoginSharedPreferences(appletCode: String, responseBody:BasicApiResponseModel<LoginResponse> ) {
        val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        val editor = sharedPreferences.edit()
        // auth token
        editor.putString(CommonSharedPreferenceConstants.AUTH_TOKEN, responseBody.data?.authToken)

        // app login subject guid
        editor.putString(CommonSharedPreferenceConstants.SUBJECT_GUID,
            responseBody.data?.subjectGuid
        )
        editor.apply()


        // Filter based on the appletCode
        val appletTenantTokenList = responseBody.data?.appletTenantTokenList
            ?.filter { it.appletCode == appletCode }

        // List of tenants found with the appletCode from filter above
        val tenantCodesList = appletTenantTokenList?.map { it.tenantCode }
        val tenantGuidList = appletTenantTokenList?.map {it.tenantGuid}

        // stock transfer applet guid
        val firstAppletGuid: String? = appletTenantTokenList?.firstOrNull()?.appletGuid

        // Store tenantCode in sharedPreferences
        if (!tenantCodesList.isNullOrEmpty()) {
            val serializedTenantCodes = tenantCodesList.joinToString(",")
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_LIST, serializedTenantCodes)
            editor.apply()

            // Put the first tenantCode as default selected in sharedPreferences
            editor.putString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, tenantCodesList.get(0))
            editor.apply()

            val serializedTenantGuids = tenantGuidList?.joinToString(",")
            editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_LIST, serializedTenantGuids)
            editor.apply()

            if (tenantGuidList != null) {
                editor.putString(CommonSharedPreferenceConstants.TENANT_GUID_SELECTED, tenantGuidList.get(0))
            }
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