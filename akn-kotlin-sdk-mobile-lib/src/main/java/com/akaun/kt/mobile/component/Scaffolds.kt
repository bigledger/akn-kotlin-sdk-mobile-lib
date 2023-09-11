package com.akaun.kt.mobile.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akaun.kt.mobile.component.appbar.BackTopBar

@Composable
fun DefaultBackScaffold(
    title: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            BackTopBar(title = title)
        },
        content = content
    )
}