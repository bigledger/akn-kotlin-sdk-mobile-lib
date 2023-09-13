package com.akaun.kt.mobile.screens.loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class CommonLoadingViewModel: ViewModel() {
    var isLoading by mutableStateOf(true)
    var isError by mutableStateOf(false)

    fun initializeCommonAppData() {
        /* TODO: Add functionality to grab common user data for app use. App projects will create
        their own viewmodel and inherit from this and call this through the
        */
    }
}