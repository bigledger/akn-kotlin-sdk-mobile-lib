package com.akaun.kt.mobile.utils

fun hasMinLength(password: String): Boolean {
    return password.length >= 8
}

fun hasNumber(password: String): Boolean {
    return password.any { it.isDigit() }
}

fun hasUpperCase(password: String): Boolean {
    return password.any { it.isUpperCase() }
}

fun hasSpecialChar(password: String): Boolean {
    return password.any { it.isLetterOrDigit().not() && it.isWhitespace().not() }
}

fun generateSupportingText(
    hasMinLength: Boolean,
    hasNumber: Boolean,
    hasUpperCase: Boolean,
    hasSpecialChar: Boolean
): String {
    val conditions = listOf(
        "at least 8 characters",
        "at least 1 number",
        "at least 1 uppercase letter",
        "at least 1 special character"
    )

    val metConditions = listOf(hasMinLength, hasNumber, hasUpperCase, hasSpecialChar)

    val unmetConditions = conditions
        .zip(metConditions)
        .filter { !it.second }
        .map { it.first }

    return if (unmetConditions.isEmpty()) {
        ""
    } else {
        "Password must have ${unmetConditions.joinToString(", ")}"
    }
}