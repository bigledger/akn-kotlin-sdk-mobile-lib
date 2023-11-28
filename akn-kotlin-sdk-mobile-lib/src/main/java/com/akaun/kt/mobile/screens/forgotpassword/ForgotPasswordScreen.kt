

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.screens.forgotpassword.ForgotPasswordScreenViewModel
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.button.LoadingButtonComponent
import com.akaun.kt.mobile.component.inputfield.LoginTextFieldComponent
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.BasicTextComponent
import com.akaun.kt.mobile.component.text.ParaTextComponent
import com.akaun.kt.mobile.component.text.TitleTextComponent
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.ConfirmForgotPassword
import com.akaun.kt.mobile.destination.Login
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.mobile.utils.isValidMobileNumber
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordInitRequest


@Composable
fun ForgotPasswordScreen(navController: NavHostController, identityPasswordScreenViewModel: ForgotPasswordScreenViewModel = hiltViewModel()) {
    val emailOrMobileNumber = rememberSaveable { mutableStateOf("") }
    val validInputs = rememberSaveable(emailOrMobileNumber.value) {
        mutableStateOf(
            (isValidEmail(emailOrMobileNumber.value.trim()) ||
                    isValidMobileNumber(emailOrMobileNumber.value.trim())))
    }
    val context = LocalContext.current

    if(identityPasswordScreenViewModel.isErrorForgotPasswordInit.value){
        Toast.makeText(context,"Email or mobile number not found",Toast.LENGTH_SHORT).show()
        identityPasswordScreenViewModel.isErrorForgotPasswordInit.value = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoComponent(width = 200.dp, height = 50.dp)

                Spacer(modifier = Modifier.height(10.dp))

                TitleTextComponent(value = stringResource(id = R.string.title_forgot_password))

                Spacer(modifier = Modifier.height(20.dp))

                ParaTextComponent(value = stringResource(id = R.string.desc_forgot_password))

                Spacer(modifier = Modifier.height(20.dp))

                LoginTextFieldComponent(valueState = emailOrMobileNumber,
                    label = stringResource(R.string.email_or_num),
                    supportingText = stringResource(R.string.email_or_num_req),
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(40.dp))

                val isLoading = identityPasswordScreenViewModel.forgotPasswordInit.value.loading!!
                LoadingButtonComponent(
                    text = "Submit" ,
                    modifier = Modifier.width(280.dp) ,
                    enabled = emailOrMobileNumber.value.isNotEmpty() && (isValidEmail(emailOrMobileNumber.value.trim()) || isValidMobileNumber(emailOrMobileNumber.value.trim())) && !isLoading,
                    loading = isLoading ){
                    identityPasswordScreenViewModel.forgotPasswordInit(
                        forgotPasswordInitRequest = ForgotPasswordInitRequest(
                            email = if(isValidEmail(emailOrMobileNumber.value.trim())) emailOrMobileNumber.value.trim() else null,
                            mobileNumber = if(isValidMobileNumber(emailOrMobileNumber.value.trim())) emailOrMobileNumber.value.trim() else null,
                        )
                    ){
                        navController.navigate(ConfirmForgotPassword.route + "/${emailOrMobileNumber.value}")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicTextComponent(value = stringResource(id = R.string.go_to_login))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(id = R.string.sign_in),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            navController.navigate(Login.route) {
                                popUpTo(AuthGraph.route) {
                                    inclusive = true
                                }
                            }
                        })
                }
            }
        }
    }
}
