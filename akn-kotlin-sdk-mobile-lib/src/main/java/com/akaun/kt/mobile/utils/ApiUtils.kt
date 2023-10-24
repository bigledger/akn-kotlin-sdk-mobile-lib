package com.akaun.kt.mobile.utils

import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config

data class BasicApiHeader(
    val authToken: String,
    val tenantCode: String
)

fun getHeaders(): BasicApiHeader {
    val sharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
    val authToken = sharedPref.getString(CommonSharedPreferenceConstants.AUTH_TOKEN, "") ?: ""
    val tenantCode = sharedPref.getString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, "") ?: ""

    return BasicApiHeader(authToken = authToken, tenantCode = tenantCode)
}

fun getBaseUrl(): String {
    val sharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)

    return sharedPref.getString(CommonSharedPreferenceConstants.BASE_URL, "")
        ?: Core2Config.CLOUD_URL
}

fun getTenantCode(): String {
    val sharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)

    return sharedPref.getString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, "")
        ?: ""
}

fun getSubjectGuid(): String {
    val sharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)

    return sharedPref.getString(CommonSharedPreferenceConstants.SUBJECT_GUID, "")
        ?: ""
}

