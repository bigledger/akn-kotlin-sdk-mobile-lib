package com.akaun.kt.mobile.core.sharedpreference

class CommonSharedPreferenceConstants {
    companion object {
        // COMMON_PREF_NAME

        // Base URL for APIs
        const val BASE_URL = "baseURL"

        // Applet Information
        const val APPLET_CODE = "appletCode"

        //Common App Information
        const val IS_USER_SIGNED_IN = "isUserSignedIn"

        // LOGIN_PREF_NAME

        // Login Preferences
        const val AUTH_TOKEN = "authToken"
        const val TENANT_CODE_LIST = "tenantCodeList"
        const val TENANT_CODE_SELECTED = "tenantCodeSelected"
        const val SUBJECT_GUID = "subjectGuid"
        const val TENANT_GUID_LIST = "tenantGuidList"
        const val TENANT_GUID_SELECTED = "tenantGuidSelected"
        const val APPLET_GUID = "appletGuid"

        // User Credentials
        const val PASSWORD = "userPassword"
        const val USER_EMAIL = "userEmail"
        const val USER_PHONE = "userPhone"
    }
}