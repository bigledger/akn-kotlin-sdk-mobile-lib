package com.akaun.kt.mobile.repository

import android.util.Log
import com.akaun.kt.sdk.models.aggregates.erp.applogin.AppLoginModel
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.userprofileservices.UserProfileService
import com.akaun.kt.sdk.utils.wrapper.DataOrException
import okhttp3.ResponseBody
import javax.inject.Inject

class UserProfileRepository constructor(private val api: UserProfileService){

    suspend fun getUserProfile()
            : DataOrException<BasicApiResponseModel<AppLoginModel>, Boolean, Exception> {
        return try{
            val response = api.getUserProfile()
            DataOrException(data = response)
        }catch (e: Exception){
            Log.d("exception", "exception msg: $e")
            DataOrException(e = e)
        }
    }

    suspend fun getUserProfilePhoto(extGuid : String)
            : DataOrException<ResponseBody, Boolean, Exception> {
        return try{
            val response = api.getUserProfilePhoto(extGuid = extGuid)
            DataOrException(data = response)
        }catch (e: Exception){
            Log.d("exception", "exception msg: $e")
            DataOrException(e = e)
        }
    }
}