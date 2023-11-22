package com.akaun.kt.mobile.utils

import android.os.Build
import androidx.annotation.RequiresApi
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts



@RequiresApi(Build.VERSION_CODES.N)
fun decodeAuthToken(authToken: String): Map<String, Any>? {
    try {
        val claims: Claims = Jwts.parserBuilder()
            .build()
            .parseClaimsJws(authToken)
            .body

        // Convert Claims to a Map for easier use
        val claimsMap: MutableMap<String, Any> = HashMap()
        claims.forEach { key, value -> claimsMap[key] = value }

        return claimsMap
    } catch (e: Exception) {
        // Handle token decoding errors
        e.printStackTrace()
        return null
    }
}