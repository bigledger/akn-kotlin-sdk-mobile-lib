package com.akaun.kt.mobile.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateToFormat: String, pattern: String): String {
    val instant = Instant.parse(dateToFormat)
    val date = instant.atZone(ZoneOffset.UTC).toLocalDate()
    return date.format(DateTimeFormatter.ofPattern(pattern))
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertUnixTimestampToLocalDateTime(timestampString: String): LocalDateTime {
    val timestamp = timestampString.toLong()
    val instant = Instant.ofEpochSecond(timestamp)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}