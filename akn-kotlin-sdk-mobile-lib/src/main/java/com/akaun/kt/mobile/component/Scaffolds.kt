package com.akaun.kt.mobile.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.akaun.kt.mobile.component.appbar.BackTopBar
import com.akaun.kt.mobile.component.appbar.MainTopBar

@Composable
fun DefaultBackScaffold(
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = { },
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            BackTopBar(
                title = title,
                actions = actions
                )
        },
        content = content
    )
}

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    title: String,
    openDrawer: () -> Unit = { },
    refreshNewTenant: () -> Unit = { },
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = bottomBar,
        topBar = {
            MainTopBar(
                title = title,
                openDrawer = openDrawer,
                refreshNewTenant = refreshNewTenant
            )
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets

    ) {
        content(it)
    }
}