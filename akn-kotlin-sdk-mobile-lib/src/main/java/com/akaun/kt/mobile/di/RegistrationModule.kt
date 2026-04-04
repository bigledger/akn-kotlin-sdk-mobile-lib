package com.akaun.kt.mobile.di

import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.idservices.IdentityRegistrationService
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kt.sdk.utils.client.RetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegistrationModule {
    fun provideRegistrationClient(): IdentityRegistrationService {
        return Retrofit.Builder()
            .baseUrl(
                CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                    .getString(CommonSharedPreferenceConstants.BASE_URL, Core2Config.CLOUD_URL)
                    ?: Core2Config.CLOUD_URL
            )
            .client(RetrofitClient.getBasicClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IdentityRegistrationService::class.java)
    }

    fun provideAuthorizedRegistrationClient(token: String): IdentityRegistrationService {
        return Retrofit.Builder()
            .baseUrl(
                CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                    .getString(CommonSharedPreferenceConstants.BASE_URL, Core2Config.CLOUD_URL)
                    ?: Core2Config.CLOUD_URL
            )
            .client(RetrofitClient.getAuthorizedClient(authorization = token, ""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IdentityRegistrationService::class.java)
    }
}
