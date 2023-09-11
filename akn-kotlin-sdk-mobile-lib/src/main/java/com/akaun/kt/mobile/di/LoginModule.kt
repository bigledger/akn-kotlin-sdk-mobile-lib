package com.akaun.kt.mobile.di

import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants

object LoginModule {
    fun provideLoginClient: LoginService{
        return Retrofit.Builder()
            .baseUrl(CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME).getString(CommonSharedPreferenceConstants.BASE_URL, Core2Config.CLOUD_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
}