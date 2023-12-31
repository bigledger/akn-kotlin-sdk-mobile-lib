package com.akaun.kt.mobile.utils

// Util to check if string is following the email format
val emailRegex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
        "*+/=?^_`{|}~-]+)*|\"(?:[\\x01" + "-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21" +
        "\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
        "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
        "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|" +
        "[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]" + ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f" +
        "\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

fun isValidEmail(email: String): Boolean {
    return emailRegex.toRegex().matches(email)
}

// Util to check if string is follow
val mobileNumberRegex = """^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$"""
fun isValidMobileNumber(mobileNumber: String): Boolean {
    return mobileNumberRegex.toRegex().matches(mobileNumber)
}
