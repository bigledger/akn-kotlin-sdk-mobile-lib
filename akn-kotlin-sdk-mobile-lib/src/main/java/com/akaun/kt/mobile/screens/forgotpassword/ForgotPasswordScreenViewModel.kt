package com.akaun.kt.mobile.screens.forgotpassword

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.di.IdentityPasswordModule
import com.akaun.kt.mobile.repository.IdentityPasswordRepository
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordInitRequest
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kt.sdk.utils.wrapper.DataOrException
import kotlinx.coroutines.launch

class ForgotPasswordScreenViewModel : ViewModel() {

    private val passwordRepo = IdentityPasswordRepository(IdentityPasswordModule.provideIdentityPasswordApi())

    val forgotPasswordInit : MutableState<DataOrException<BasicApiResponseModel<String?>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, false, null))

    var isErrorForgotPasswordInit= mutableStateOf(false)

    fun forgotPasswordInit(forgotPasswordInitRequest: ForgotPasswordInitRequest , onSuccessful : () -> Unit){

        viewModelScope.launch {


            forgotPasswordInit.value = forgotPasswordInit.value.copy(loading = true)

            val results = passwordRepo.forgotPasswordInit(forgotPasswordInitRequest)

            // Update the data with the result
            forgotPasswordInit.value = forgotPasswordInit.value.copy(data = results.data)
            forgotPasswordInit.value = forgotPasswordInit.value.copy(e = results.e)

            if(results.e != null){
                isErrorForgotPasswordInit.value = true
            }else {
                onSuccessful()
            }


            forgotPasswordInit.value = forgotPasswordInit.value.copy(loading = false)

        }
    }


}