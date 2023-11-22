package com.akaun.kt.mobile.screens.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(
    toAuth: () -> Unit,
    toLoading: () -> Unit,
    setDefaultEnvironment : () -> Unit,
    viewModel: SplashViewModel = viewModel()
    ) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        if (viewModel.isSignedIn() && !viewModel.isAuthTokenExpired()) {
            toLoading()
        } else {
            setDefaultEnvironment()
            toAuth()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LogoComponent(width = 300.dp, height = 100.dp)
    }
}