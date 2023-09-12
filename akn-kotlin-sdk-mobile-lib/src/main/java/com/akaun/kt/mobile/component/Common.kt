package com.akaun.kt.mobile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Spacer6() = Spacer(modifier = Modifier.padding(6.dp))

@Composable
fun Spacer6v() = Spacer(modifier = Modifier.padding(vertical = 6.dp))

@Composable
fun Spacer4() = Spacer(modifier = Modifier.padding(4.dp))

@Composable
fun Spacer4h() = Spacer(modifier = Modifier.padding(horizontal = 4.dp))

@Composable
fun Spacer8h() = Spacer(modifier = Modifier.padding(horizontal = 8.dp))

@Composable
fun Spacer12() = Spacer(modifier = Modifier.padding(12.dp))

@Composable
fun Spacer8v() = Spacer(modifier = Modifier.padding(vertical = 8.dp))

@Composable
fun Spacer16v() = Spacer(modifier = Modifier.padding(vertical = 16.dp))

@Composable
fun Spacer32v() = Spacer(modifier = Modifier.padding(vertical = 32.dp))

@Composable
fun Padding6h() = Modifier.padding(horizontal = 6.dp)

@Composable
fun Padding8h() = Modifier.padding(horizontal = 8.dp)

@Composable
fun Padding12h() = Modifier.padding(horizontal = 12.dp)

@Composable
fun Padding16h() = Modifier.padding(horizontal = 16.dp)

@Composable
fun Div6v() = Divider(modifier = Modifier.padding(vertical = 6.dp))

@Composable
fun MainTitle(title: String){
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SubTitleSmall(
    title: String,
    modifier: Modifier = Modifier
){
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(start = 4.dp)
    )
}

@Composable
fun SubTitleLarge(
    title: String,
    modifier: Modifier = Modifier
){
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(start = 4.dp)
    )
}

@Composable
fun MainMessage(message: String){
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
fun MainWarningMessage(message: String){
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
fun SmallText(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(start = 2.dp)
    )
}

@Composable
fun CheckBoxWithText(
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String
){
    Row(verticalAlignment = Alignment.CenterVertically){
        Checkbox(
            enabled = enabled,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}