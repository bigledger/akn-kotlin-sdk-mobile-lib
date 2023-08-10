package com.akaun.kt.mobile.component.multimedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.R

@Composable
fun AkaunLogo(size: Dp) {
    Image(painter = painterResource(id = R.drawable.akaun),
        contentDescription = "akaun logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(size))
}

@Composable
@Preview()
private fun AkaunLogoPreview() {
    AkaunLogo(size = 200.dp)
}

