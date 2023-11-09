package com.akaun.kt.mobile.screens.login
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.ForgotPassword
import com.akaun.kt.mobile.destination.LoadingGraph
import com.akaun.kt.mobile.destination.MainAppGraph
import com.akaun.kt.mobile.destination.Register
import com.akaun.kt.mobile.destination.ResendVerification
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.mobile.utils.isValidMobileNumber
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = viewModel(),
    googleSignInClient: GoogleSignInClient,
    googleClientId: String,
    toRegister: () -> Unit,
    toResendVerification: () -> Unit,
    toForgotPassword: () -> Unit,
    onSignIn: () -> Unit,

) {
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
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    // For google sign in
    val launcher =
        rememberLauncherForActivityResult(GoogleSignContract(googleSignInClient)) { googleLoginResult ->
            scope.launch {
                when (googleLoginResult) {
                    is GoogleLoginResult.Success -> {
                         Log.d("SIGN IN LAUNCH", "Google Login Success: ${googleLoginResult.googleIdToken}")
                        viewModel.signInWithGoogle(
                            googleToken = googleLoginResult.googleIdToken ?: "",
                            googleClientId = googleClientId,
                            appletCode = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                                .getString(CommonSharedPreferenceConstants.APPLET_CODE, "") ?: ""){
                            onSignIn()
                        }
                    }
                    is GoogleLoginResult.Error -> {
                        Log.d("SIGN IN LAUNCH", "Google Login Error: ${googleLoginResult.errorMessage}")
                    }
                    is GoogleLoginResult.UserCanceled -> {
                        Log.d("SIGN IN LAUNCH", "Google Login User Cancelled or smthg went wrong with calling google api (problem with google client id and others) ")
                    }
                }
            }
        }

    if (isError) {
        viewModel.resetIsError()
        Toast.makeText(context, "An error occurred!", Toast.LENGTH_SHORT).show()
    }

    if (isInvalid) {
        viewModel.resetIsInvalid()
        Toast.makeText(context, "Incorrect credentials, sign in failed.", Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoComponent(width = 200.dp, height = 50.dp)

                Spacer(modifier = Modifier.height(10.dp))

                Column(horizontalAlignment = Alignment.Start) {
                    TitleTextComponent(value = stringResource(R.string.sign_in), modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(15.dp))

                    LoginTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        valueState = emailOrMobileNumber,
                        label = stringResource(R.string.email_or_num),
                        supportingText = stringResource(R.string.email_or_num_req)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        passwordValueState = password,
                        imeAction = ImeAction.Done,
                        label = stringResource(R.string.password),
                        supportingText = stringResource(R.string.password_req))
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(horizontalAlignment = Alignment.End) {
                    BasicTextComponent(value = stringResource(R.string.forgot_password),
                        modifier = Modifier.clickable {
                            toForgotPassword()
                        })

                    Spacer(modifier = Modifier.height(5.dp))

                    LoadingButtonComponent(text = stringResource(R.string.sign_in),
                        enabled = validInputs.value,
//                        loading = isLoading,
                        modifier = Modifier.fillMaxWidth()) {
                        keyboardController?.hide()
                        viewModel.signInWithEmailOrMobileWithPassword(emailOrMobileNumber = emailOrMobileNumber.value.trim(),
                            password = password.value.trim(),
                            appletCode = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
                                .getString(CommonSharedPreferenceConstants.APPLET_CODE, "") ?: ""){
                            onSignIn()
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    CommonButtonComponent(text = stringResource(R.string.resend_verification),
                        variant = true , modifier = Modifier.fillMaxWidth()){
                        toResendVerification()
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                DividerTextComponent(value = stringResource(R.string.or_sign_with))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
//                    SocialButtonComponent(icon = painterResource(id = R.drawable.apple))
                    SocialButtonComponent(
                        modifier = Modifier.fillMaxWidth(),
                        icon = painterResource(id = R.drawable.google),
                        onClick = {
                            launcher.launch(null)
                        }
                    )
//                    SocialButtonComponent(icon = painterResource(id = R.drawable.facebook))
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
                            toRegister()
                        })
                }
            }
        }
        // Show overlay if loading
        if (isLoading) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .pointerInput(Unit) {
                        detectTapGestures {
                            // Do nothing to block touch events while log in attempt
                            Log.d("LOAD BOX", "Touching while loading, LoginScreen")
                        }
                    }
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}

