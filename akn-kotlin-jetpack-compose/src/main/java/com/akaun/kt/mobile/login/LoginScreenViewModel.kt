package com.akaun.kt.mobile.login
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.utils.isValidEmail
import kotlinx.coroutines.launch


class LoginScreenViewModel: ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
        private set

    var isInvalid by mutableStateOf(false)
        private set

    fun resetIsError(): Unit {
        isError = false
    }

    fun resetIsInvalid(): Unit {
        isInvalid = false
    }

    fun signInWithEmailOrMobileWithPassword(
        emailOrMobileNumber: String,
        password: String,
        proceed: () -> Unit
    ) = viewModelScope.launch {
        isLoading = true
        //TODO: Use the common shared preferences constants
        try {
            val loginRequest = if (isValidEmail(emailOrMobileNumber)) {
                LoginRequest(email = emailOrMobileNumber, password = password)
            } else {
                LoginRequest(mobileNumber = emailOrMobileNumber, password = password)
            }
            val loginAPI = AppModule().provideLoginApi()
            val call = loginAPI.login(loginRequest)
            call.enqueue(object : Callback<BasicApiResponseModel<LoginContainer>> {
                @SuppressLint("CommitPrefEdits")
                override fun onResponse(call: Call<BasicApiResponseModel<LoginContainer>>, response: Response<BasicApiResponseModel<LoginContainer>>) {
                    if (response.isSuccessful) {
                        val responseBody: BasicApiResponseModel<LoginContainer>? = response.body()
                        if (responseBody != null) {
                            // Store the authToken in sharedPreferences
                            val sharedPreferences = AppSingleton.appContext.getSharedPreferences("userLoginPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("authToken",responseBody.data.authToken)
                            editor.apply()


                            // Retrieve list of tenants user is linked to
                            val tenantCodesList = responseBody.data.appletTenantTokenList
                                .filter { it.appletCode == "stockTransferApplet" }
                                .map { it.tenantCode }

                            Log.d("login", "onResponse: $tenantCodesList")
                            // Store tenantCode in sharedPreferences
                            if (tenantCodesList.isNotEmpty()) {
                                val serializedTenantCodes = tenantCodesList.joinToString(",")
                                editor.putString("tenantCodeList", serializedTenantCodes)
                                editor.apply()

                                // Put the first tenantCode as default selected in sharedPreferences
                                editor.putString("tenantCodeSelected", tenantCodesList.get(0))
                                editor.apply()

                            } else {
                                editor.putString("tenantCodeList",null)
                                editor.putString("tenantCodeSelected",null)
                                editor.apply()
                            }
                            // Proceed to main page
                            proceed()
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
                override fun onFailure(call : Call<BasicApiResponseModel<LoginContainer>>, t: Throwable) {
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


}