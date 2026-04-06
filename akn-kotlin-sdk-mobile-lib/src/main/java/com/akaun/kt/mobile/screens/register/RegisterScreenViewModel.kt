package com.akaun.kt.mobile.screens.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.di.RegistrationModule
import com.akaun.kt.sdk.models.dbschema.RegistrationRequest
import kotlinx.coroutines.launch

class RegisterScreenViewModel : ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var isSuccess by mutableStateOf(false)
        private set

    fun resetIsError() {
        isError = false
        errorMessage = ""
    }

    fun resetIsSuccess() {
        isSuccess = false
    }

    fun registerWithEmail(
        email: String,
        firstName: String,
        lastName: String
    ) = viewModelScope.launch {
        isLoading = true
        try {
            // Step 1: Generate JWT token without captcha
            Log.d(TAG, "Step 1: Generating JWT token without captcha...")
            val registrationClient = RegistrationModule.provideRegistrationClient()
            val tokenResult = registrationClient.generateJWTTokenWithoutCaptcha()
            Log.d(TAG, "Step 1 result - code: ${tokenResult.code}, message: ${tokenResult.message}, hasData: ${tokenResult.data != null}")

            if (tokenResult.data == null) {
                isError = true
                errorMessage = tokenResult.message ?: "Failed to generate registration token."
                isLoading = false
                return@launch
            }

            // Step 2: Register with the token
            Log.d(TAG, "Step 2: Registering with email using token...")
            val authorizedClient = RegistrationModule.provideAuthorizedRegistrationClient(tokenResult.data!!)
            val registrationRequest = RegistrationRequest(
                email = email,
                nameFirst = firstName,
                nameLast = lastName
            )

            val result = authorizedClient.registerWithEmail(registrationRequest)
            Log.d(TAG, "Step 2 result - code: ${result.code}, message: ${result.message}")

            if (result.code == "OK" || result.data != null) {
                isSuccess = true
            } else {
                isError = true
                errorMessage = result.message ?: "Registration failed."
            }
        } catch (ex: Exception) {
            isError = true
            errorMessage = ex.message ?: "An unexpected error occurred."
            Log.d(TAG, "Registration failed: ${ex.javaClass.simpleName} - ${ex.message}")
        } finally {
            isLoading = false
        }
    }

    fun registerWithMobileNumber(
        mobileNumber: String,
        firstName: String,
        lastName: String
    ) = viewModelScope.launch {
        isLoading = true
        try {
            Log.d(TAG, "Step 1: Generating JWT token without captcha...")
            val registrationClient = RegistrationModule.provideRegistrationClient()
            val tokenResult = registrationClient.generateJWTTokenWithoutCaptcha()
            Log.d(TAG, "Step 1 result - code: ${tokenResult.code}, message: ${tokenResult.message}, hasData: ${tokenResult.data != null}")

            if (tokenResult.data == null) {
                isError = true
                errorMessage = tokenResult.message ?: "Failed to generate registration token."
                isLoading = false
                return@launch
            }

            Log.d(TAG, "Step 2: Registering with mobile number using token...")
            val authorizedClient = RegistrationModule.provideAuthorizedRegistrationClient(tokenResult.data!!)
            val registrationRequest = RegistrationRequest(
                mobileNumber = mobileNumber,
                nameFirst = firstName,
                nameLast = lastName
            )

            val result = authorizedClient.registerWithMobileNumber(registrationRequest)
            Log.d(TAG, "Step 2 result - code: ${result.code}, message: ${result.message}")

            if (result.code == "OK" || result.data != null) {
                isSuccess = true
            } else {
                isError = true
                errorMessage = result.message ?: "Registration failed."
            }
        } catch (ex: Exception) {
            isError = true
            errorMessage = ex.message ?: "An unexpected error occurred."
            Log.d(TAG, "Registration failed: ${ex.javaClass.simpleName} - ${ex.message}")
        } finally {
            isLoading = false
        }
    }
}
