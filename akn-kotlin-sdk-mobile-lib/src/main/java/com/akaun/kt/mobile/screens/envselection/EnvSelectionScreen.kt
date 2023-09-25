package com.akaun.kt.mobile.screens.envselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.component.button.CommonButtonComponent
import com.akaun.kt.mobile.component.dropdown.BasicDropDown
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.TitleLargeText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akaun.kt.mobile.component.dropdown.DropdownMenuBox
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config


sealed class Environment(val value: String, val url: String) {
    object Dev: Environment("akaun.dev", Core2Config.DEV_URL)
    object Cloud: Environment("akaun.cloud", Core2Config.CLOUD_URL)
    object Production: Environment("akaun.com", Core2Config.DOMAIN_URL)
}

val environmentList = listOf(
    Environment.Production.value,
    Environment.Cloud.value,
    Environment.Dev.value,
)



@Composable
fun EnvSelectionScreen(
    toSignIn: () -> Unit,
    viewModel: EnvSelectionViewModel = viewModel()
    ) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            LogoComponent(width = 200.dp, height = 50.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                ) {
                TitleLargeText(text = "Choose the Environment")

                DropdownMenuBox(
                    label = "Environment",
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    dropDownItemList = environmentList
                ) {environment ->

                    when (environment) {
                        Environment.Production.value -> {
                            viewModel.setEnvironment(Environment.Production.url)
                        }
                        Environment.Cloud.value -> {
                            viewModel.setEnvironment(Environment.Cloud.url)
                        }
                        Environment.Dev.value -> {
                            viewModel.setEnvironment(Environment.Dev.url)
                        }
                    }
                }
            }

            CommonButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = "Proceed",
                enabled = viewModel.isEnvSelected
                ) {
                toSignIn()
            }
        }
    }
}

@Preview
@Composable
fun PreviewEnvScreen() {
    EnvSelectionScreen({})
}
