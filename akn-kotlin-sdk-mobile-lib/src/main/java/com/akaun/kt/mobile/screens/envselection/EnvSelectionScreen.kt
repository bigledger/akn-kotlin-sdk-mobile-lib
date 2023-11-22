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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.component.button.CommonButtonComponent
import com.akaun.kt.mobile.component.dropdown.BasicDropDown
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.TitleLargeText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akaun.kt.mobile.component.DefaultBackScaffold
import com.akaun.kt.mobile.component.dropdown.DropdownMenuBox
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config


sealed class Environment(val value: String, val url: String) {
    object Dev: Environment("akaun.dev", Core2Config.DEV_URL)
    object Cloud: Environment("akaun.cloud", Core2Config.CLOUD_URL)
    object Production: Environment("akaun.com", Core2Config.DOMAIN_URL)

    companion object {
        fun fromUrl(url: String): Environment? {
            return when (url) {
                Core2Config.DEV_URL -> Dev
                Core2Config.CLOUD_URL -> Cloud
                Core2Config.DOMAIN_URL -> Production
                else -> null
            }
        }
    }
}

val environmentList = listOf(
    Environment.Production.value,
    Environment.Cloud.value,
    Environment.Dev.value,
)



@Composable
fun EnvSelectionScreen(
    toSignIn: () -> Unit,
    devAppletCode: String,
    cloudAppletCode: String,
    productionAppletCode: String,
    viewModel: EnvSelectionViewModel = viewModel()
    ) {

    DefaultBackScaffold(title = "") {
        Surface(modifier = Modifier.padding(it).fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LogoComponent(width = 200.dp, height = 50.dp)

                // Get currently selected URL
                val url = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME).getString(
                    CommonSharedPreferenceConstants.BASE_URL,"") ?: ""

                // Find back the environment object so that can display the environment name using the url
                val environment = Environment.fromUrl(url)
                val envSelected = remember{
                    mutableStateOf(environment?.value ?: "")
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TitleLargeText(text = "Choose the Environment")

                    DropdownMenuBox(
                        label = "Environment",
                        modifier = Modifier.fillMaxWidth(),
                        value = envSelected.value,
                        dropDownItemList = environmentList
                    ) {environment ->
                        envSelected.value = environment
                    }
                }

                CommonButtonComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Select & Save"
                ) {

                    if(envSelected.value ==Environment.Production.value ){
                        viewModel.setEnvironment(Environment.Production.url)
                        viewModel.setEnvironmentAppletCode(productionAppletCode)
                    }else if (envSelected.value ==Environment.Cloud.value){
                        viewModel.setEnvironment(Environment.Cloud.url)
                        viewModel.setEnvironmentAppletCode(cloudAppletCode)
                    }else{ // dev
                        viewModel.setEnvironment(Environment.Dev.url)
                        viewModel.setEnvironmentAppletCode(devAppletCode)
                    }
                    toSignIn()
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewEnvScreen() {
//    EnvSelectionScreen(""{})
}
