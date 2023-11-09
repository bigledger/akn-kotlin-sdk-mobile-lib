package com.akaun.kt.mobile.screens.login


sealed class GoogleLoginResult {
    /**
     * Indicates User denied Camara Permission.
     */
    data class Success internal constructor(val googleIdToken: String?) : GoogleLoginResult()
    /**
     * Indicates error occurred when tyring to get google sign in
     */
    data class Error internal constructor(val errorMessage: String) : GoogleLoginResult()

    /**
     * User pressed back on google sign in options listing
     */
    object UserCanceled : GoogleLoginResult()
}