
package com.akaun.kt.mobile.component.text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextComponent(value: String, modifier: Modifier = Modifier) {
    Text(
        text = value,
        modifier = modifier,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun BasicTextComponentPreview() {
    Column {
        BasicTextComponent(value = "Hello World")
    }
}