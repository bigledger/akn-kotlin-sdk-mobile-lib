package com.akaun.kt.mobile.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val buttonSize = 280.dp
@Composable
fun CommonButton(text: String, enabled: Boolean = true, variant:Boolean = false, onClick: () -> Unit = {}) {
    val colors = if (variant) {
        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    } else {
        ButtonDefaults.buttonColors()
    }
    Button(enabled = enabled, onClick = onClick,
        modifier = Modifier.width(buttonSize),
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

@Composable
@Preview(showSystemUi = true)
private fun CommonButtonPreview() {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        CommonButton(text = "Sign In")
        CommonButton(text = "Resend Verification", variant = true)
    }
}
