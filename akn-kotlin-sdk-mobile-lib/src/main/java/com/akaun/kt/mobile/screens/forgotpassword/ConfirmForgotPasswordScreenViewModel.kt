package com.akaun.kt.mobile.screens.forgotpassword

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.di.IdentityPasswordModule
import com.akaun.kt.mobile.repository.IdentityPasswordRepository
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordConfirmRequest
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kt.sdk.utils.wrapper.DataOrException
import kotlinx.coroutines.launch



class ConfirmForgotPasswordScreenViewModel : ViewModel(){

    private val passwordRepo = IdentityPasswordRepository(IdentityPasswordModule.provideIdentityPasswordApi())

    val forgotPasswordConfirm : MutableState<DataOrException<BasicApiResponseModel<String?>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, false, null))


    fun forgotPasswordConfirm(forgotPasswordConfirmRequest: ForgotPasswordConfirmRequest,onFailure :() -> Unit  ,onSuccessful : () -> Unit){

        viewModelScope.launch {


            forgotPasswordConfirm.value = forgotPasswordConfirm.value.copy(loading = true)

            val results = passwordRepo.forgotPasswordConfirm(forgotPasswordConfirmRequest)

            // Update the data with the result
            forgotPasswordConfirm.value = forgotPasswordConfirm.value.copy(data = results.data)
            forgotPasswordConfirm.value = forgotPasswordConfirm.value.copy(e = results.e)

            if(results.e != null){
                onFailure()
            }else {
                onSuccessful()
            }
            forgotPasswordConfirm.value = forgotPasswordConfirm.value.copy(loading = false)

        }
    }
}