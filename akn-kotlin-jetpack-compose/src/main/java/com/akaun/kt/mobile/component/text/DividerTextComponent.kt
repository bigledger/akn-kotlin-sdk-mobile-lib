package com.akaun.kt.mobile.component.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DividerTextComponent(value: String) {
    val color = MaterialTheme.colorScheme.secondary
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = color,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = value,
            fontSize = 18.sp,
            color = color
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = color,
            thickness = 1.dp
        )
    }
}