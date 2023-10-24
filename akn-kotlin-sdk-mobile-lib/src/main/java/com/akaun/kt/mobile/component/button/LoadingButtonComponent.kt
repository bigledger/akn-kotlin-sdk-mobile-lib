

package com.akaun.kt.mobile.component.button
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LoadingButtonComponent(text: String,
                           modifier : Modifier = Modifier,
                           enabled: Boolean = true,
                           loading: Boolean = false,
                           variant:Boolean = false,
                           defaultElevation: Dp = 10.dp,
                           pressedElevation: Dp = 5.dp,
                           disabledElevation: Dp = 0.dp,
                           onClick: () -> Unit = {}) {
    val colors = if (variant) {
        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    } else {
        ButtonDefaults.buttonColors()
    }

    Button(enabled = enabled, onClick = onClick,
        modifier = modifier ,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = defaultElevation,
            pressedElevation = pressedElevation,
            disabledElevation = disabledElevation,
        ),
        colors = colors
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp))
        } else {
            Text(text = text)
        }
    }
}