package com.akaun.kt.mobile.component.button
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommonButtonComponent(text: String,
                          modifier : Modifier = Modifier ,
                          enabled: Boolean = true,
                          variant:Boolean = false,
                          onClick: () -> Unit = {},) {
    val colors = if (variant) {
        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    } else {
        ButtonDefaults.buttonColors()
    }

    Button(enabled = enabled, onClick = onClick,
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 5.dp,
            disabledElevation = 0.dp,
        ),
        colors = colors
    ) {
        Text(text = text)
    }
}

