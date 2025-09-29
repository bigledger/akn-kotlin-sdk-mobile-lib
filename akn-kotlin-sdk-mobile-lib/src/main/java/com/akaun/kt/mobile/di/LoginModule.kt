package com.akaun.kt.mobile.di

import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.idservices.LoginService
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kt.sdk.utils.client.RetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginModule {
    fun provideLoginClient(): LoginService {
        return Retrofit.Builder()
            .baseUrl(
                CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                    .getString(CommonSharedPreferenceConstants.BASE_URL, Core2Config.CLOUD_URL) ?:
                    Core2Config.CLOUD_URL)
            .client(RetrofitClient.getBasicClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }

    fun provideUserAppletLinkClient(auth: String): LoginService {
        return Retrofit.Builder()
            .baseUrl(
                CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                    .getString(CommonSharedPreferenceConstants.BASE_URL, Core2Config.CLOUD_URL) ?:
                Core2Config.CLOUD_URL)
            .client(RetrofitClient.getAuthorizedClient(authorization = auth, ""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }
}