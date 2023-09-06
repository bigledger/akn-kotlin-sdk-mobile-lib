
package com.akaun.kt.mobile.component.button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.R


@Composable
fun SocialButtonComponent(text: String = "", icon: Painter, onClick: () -> Unit = {}) {
    val colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary)
    val buttonWidth = 90.dp
    val buttonStrokeWidth = 1.dp
    val buttonBorderColor = MaterialTheme.colorScheme.primary

    Button(modifier = Modifier.widthIn(min = buttonWidth),
        border = BorderStroke(buttonStrokeWidth, buttonBorderColor),
        onClick = onClick,
        colors = colors,
        contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
    ) {
        Icon(painter = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary)
//        Spacer(modifier = Modifier.size(10.dp))
        Text(text = text,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview(showSystemUi = true)
private fun SocialButtonPreview() {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        SocialButtonComponent(text = "Apple ID", icon = painterResource(id = R.drawable.apple))
        SocialButtonComponent(text = "Google", icon = painterResource(id = R.drawable.google))
        SocialButtonComponent(text = "Facebook", icon = painterResource(id = R.drawable.facebook))
    }

}
