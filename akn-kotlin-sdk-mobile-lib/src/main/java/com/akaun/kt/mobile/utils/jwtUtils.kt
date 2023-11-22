package com.akaun.kt.mobile.utils


import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT




fun extractPayload(jwtToken: String): Map<String, Any>? {
    try {
        val decodedJWT: DecodedJWT = JWT.decode(jwtToken)
        val claimsMap: MutableMap<String, Any> = HashMap()

        decodedJWT.claims.forEach { (key, value) ->
            claimsMap[key] = value // Convert the claim values to strings
        }

        return claimsMap
    } catch (e: Exception) {
        // Handle token decoding errors
        e.printStackTrace()
        return null
    }
}