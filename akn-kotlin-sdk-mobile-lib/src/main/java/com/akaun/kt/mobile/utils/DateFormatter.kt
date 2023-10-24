package com.akaun.kt.mobile.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateToFormat: String, pattern: String): String {
    val instant = Instant.parse(dateToFormat)
    val date = instant.atZone(ZoneOffset.UTC).toLocalDate()
    return date.format(DateTimeFormatter.ofPattern(pattern))
}