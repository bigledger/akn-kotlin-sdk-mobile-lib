package com.akaun.kt.mobile.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FloatingActionButton() {
    FloatingActionButton(
        onClick = { /* do something */ },
    ) {
        Icon(Icons.Filled.Add, "Localized description")
    }
}

@Composable
fun ExtendedFloatingActionButton() {
    ExtendedFloatingActionButton(onClick = { /* do something */ }) {
        Text(text = "Extended FAB")
    }
}