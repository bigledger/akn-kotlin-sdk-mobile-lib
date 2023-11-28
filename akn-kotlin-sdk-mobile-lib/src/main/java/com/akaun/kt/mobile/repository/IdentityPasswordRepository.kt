package com.akaun.kt.mobile.repository

import android.util.Log
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordConfirmRequest
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordInitRequest
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.idservices.IdentityPasswordService
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kt.sdk.utils.wrapper.DataOrException
import javax.inject.Inject

class IdentityPasswordRepository @Inject constructor(private val api: IdentityPasswordService){

    suspend fun forgotPasswordInit(forgotPasswordInitRequest : ForgotPasswordInitRequest)
            : DataOrException<BasicApiResponseModel<String?>, Boolean, Exception> {
        return try{
            val response = api.forgotPasswordInit(forgotPasswordInitRequest = forgotPasswordInitRequest)
            DataOrException(data = response)
        }catch (e: Exception){
            Log.d("exception", "exception msg: $e")
            DataOrException(e = e)
        }
    }

    suspend fun forgotPasswordConfirm(forgotPasswordConfirmRequest : ForgotPasswordConfirmRequest)
            : DataOrException<BasicApiResponseModel<String?>, Boolean, Exception> {
        return try{
            val response = api.forgotPasswordConfirm(forgotPasswordConfirmRequest = forgotPasswordConfirmRequest)
            DataOrException(data = response)
        }catch (e: Exception){
            Log.d("exception", "exception msg: $e")
            DataOrException(e = e)
        }
    }


}