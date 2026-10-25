package com.akaun.kt.mobile.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUtilsTest {
    @Test
    fun isValidEmail_acceptsUppercase() {
        assertTrue(isValidEmail("Akibul@Wavelet.net"))
        assertTrue(isValidEmail("JOHN.DOE@GMAIL.COM"))
    }

    @Test
    fun isValidEmail_acceptsLowercase() {
        assertTrue(isValidEmail("a.b+c@x.co.uk"))
    }

    @Test
    fun isValidEmail_rejectsInvalid() {
        assertFalse(isValidEmail(""))
        assertFalse(isValidEmail("john@"))
        assertFalse(isValidEmail("john.gmail.com"))
    }
}
