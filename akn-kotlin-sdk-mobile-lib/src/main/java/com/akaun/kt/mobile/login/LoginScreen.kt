package com.akaun.kt.mobile.login
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.button.CommonButtonComponent
import com.akaun.kt.mobile.component.button.LoadingButtonComponent
import com.akaun.kt.mobile.component.button.SocialButtonComponent
import com.akaun.kt.mobile.component.inputfield.LoginTextFieldComponent
import com.akaun.kt.mobile.component.inputfield.PasswordTextFieldComponent
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.BasicTextComponent
import com.akaun.kt.mobile.component.text.DividerTextComponent
import com.akaun.kt.mobile.component.text.TitleTextComponent
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.ForgotPassword
import com.akaun.kt.mobile.destination.MainAppGraph
import com.akaun.kt.mobile.destination.Register
import com.akaun.kt.mobile.destination.ResendVerification
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.mobile.utils.isValidMobileNumber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginScreenViewModel = viewModel()) {
    val isLoading = viewModel.isLoading
    val isError = viewModel.isError
    val isInvalid = viewModel.isInvalid
    val emailOrMobileNumber = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val validInputs = rememberSaveable(emailOrMobileNumber.value, password.value, isLoading) {
        mutableStateOf(
            ((isValidEmail(emailOrMobileNumber.value.trim()) ||
                    isValidMobileNumber(emailOrMobileNumber.value.trim()))
                    && password.value.trim().isNotEmpty()) && !isLoading)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    if (isError) {
        viewModel.resetIsError()
        Toast.makeText(context, "An error occured!", Toast.LENGTH_SHORT).show()
    }

    if (isInvalid) {
        viewModel.resetIsInvalid()
        Toast.makeText(context, "incorrect credentials", Toast.LENGTH_SHORT).show()
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

                Column(horizontalAlignment = Alignment.Start) {
                    TitleTextComponent(value = stringResource(R.string.sign_in))

                    Spacer(modifier = Modifier.height(10.dp))

                    LoginTextFieldComponent(valueState = emailOrMobileNumber,
                        label = stringResource(R.string.email_or_num),
                        supportingText = stringResource(R.string.email_or_num_req)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordTextFieldComponent(passwordValueState = password,
                        imeAction = ImeAction.Done,
                        label = stringResource(R.string.password),
                        supportingText = stringResource(R.string.password_req))
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(horizontalAlignment = Alignment.End) {
                    BasicTextComponent(value = stringResource(R.string.forgot_password),
                        modifier = Modifier.clickable {
                            navController.navigate(ForgotPassword.route) {
                                popUpTo(AuthGraph.route) {
                                    inclusive
                                }
                            }
                        })

                    Spacer(modifier = Modifier.height(5.dp))

                    LoadingButtonComponent(text = stringResource(R.string.sign_in),
                        enabled = validInputs.value,
                        loading = isLoading,
                        modifier = Modifier.width(280.dp)) {
                        keyboardController?.hide()
                        viewModel.signInWithEmailOrMobileWithPassword(emailOrMobileNumber.value.trim(), password.value.trim()){
                            navController.navigate(MainAppGraph.route) {
                                popUpTo(AuthGraph.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    CommonButtonComponent(text = stringResource(R.string.resend_verification),
                        variant = true , modifier = Modifier.width(280.dp)){
                        navController.navigate(ResendVerification.route)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                DividerTextComponent(value = stringResource(R.string.or_sign_with))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    SocialButtonComponent(icon = painterResource(id = R.drawable.apple))
                    SocialButtonComponent(icon = painterResource(id = R.drawable.google))
                    SocialButtonComponent(icon = painterResource(id = R.drawable.facebook))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicTextComponent(value = stringResource(id = R.string.no_account_register))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(id = R.string.register),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            navController.navigate(Register.route) {
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

