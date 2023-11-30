package com.akaun.kt.mobile.di

import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.idservices.IdentityPasswordService
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.userprofileservices.UserProfileService
import com.akaun.kt.sdk.utils.client.RetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserProfileModule {

    fun provideUserProfileApi() : UserProfileService {
        val sharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        val auth = sharedPref.getString(CommonSharedPreferenceConstants.AUTH_TOKEN, "") ?: ""
        val tenantCode = sharedPref.getString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, "") ?: ""
        return Retrofit.Builder()
            .baseUrl(
                CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME).getString(
                    CommonSharedPreferenceConstants.BASE_URL,"") ?: "")
            .client(RetrofitClient.getAuthorizedClient(auth,tenantCode))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserProfileService::class.java)
    }
}