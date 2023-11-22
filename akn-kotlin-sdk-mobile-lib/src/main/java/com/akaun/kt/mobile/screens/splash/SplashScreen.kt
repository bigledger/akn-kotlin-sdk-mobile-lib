package com.akaun.kt.mobile.screens.splash

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        delay(2000L)
        // If signed in and token not expired
        if (viewModel.isSignedIn() && !viewModel.isAuthTokenExpired()) {
            toLoading()
        }else {
            // If signed in and token already expired
            if(viewModel.isSignedIn() && viewModel.isAuthTokenExpired()){
                Toast.makeText(context,"Auth token has expired.Please sign in again", Toast.LENGTH_LONG).show()
            }

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