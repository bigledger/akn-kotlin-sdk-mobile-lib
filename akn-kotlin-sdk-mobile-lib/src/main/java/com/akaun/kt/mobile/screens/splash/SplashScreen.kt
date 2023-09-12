package com.akaun.kt.mobile.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.SplashGraph
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, ) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(AuthGraph.route) {
            popUpTo(SplashGraph.route) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LogoComponent(width = 300.dp, height = 100.dp)
    }
}