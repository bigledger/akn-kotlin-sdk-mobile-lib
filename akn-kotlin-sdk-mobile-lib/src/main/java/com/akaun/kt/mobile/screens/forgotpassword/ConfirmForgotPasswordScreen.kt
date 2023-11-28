package com.akaun.kt.mobile.screens.forgotpassword

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.button.LoadingButtonComponent
import com.akaun.kt.mobile.component.inputfield.BasicTextFieldComponent
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.BasicTextComponent
import com.akaun.kt.mobile.component.text.TitleTextComponent
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.Login
import com.akaun.kt.mobile.utils.generateSupportingText
import com.akaun.kt.mobile.utils.hasMinLength
import com.akaun.kt.mobile.utils.hasNumber
import com.akaun.kt.mobile.utils.hasSpecialChar
import com.akaun.kt.mobile.utils.hasUpperCase
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.mobile.utils.isValidMobileNumber
import com.akaun.kt.sdk.models.dbschema.ForgotPasswordConfirmRequest

@Composable
fun ConfirmForgotPasswordScreen(navController: NavHostController , emailOrMobileNumber : String , confirmForgotPasswordScreenViewModel: ConfirmForgotPasswordScreenViewModel = hiltViewModel()) {
    val tacCode = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            val scrollState = rememberScrollState()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                LogoComponent(width = 200.dp, height = 50.dp)

                Spacer(modifier = Modifier.height(10.dp))

                TitleTextComponent(value = "Reset Password")

                Spacer(modifier = Modifier.height(20.dp))


                BasicTextFieldComponent(
                    label = "Tac Code/Code",
                    value = tacCode.value,
                    supportingText = {
                        if (tacCode.value.isEmpty()) {
                            Text("Code is required")
                        }
                    },
                    onValueChange = { tacCode.value = it },
                    imeAction = ImeAction.Next,
                    isError = tacCode.value.isEmpty()
                )

                Spacer(modifier = Modifier.height(30.dp))

                NewPasswordTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    label = "Password",
                    imeAction = ImeAction.Next,
                    invalidPassword = !hasMinLength(password.value) || !hasUpperCase(password.value) || !hasNumber(password.value) || !hasSpecialChar(password.value),
                    supportingText =
                        generateSupportingText(
                            hasMinLength(password.value),
                            hasNumber(password.value),
                            hasUpperCase(password.value),
                            hasSpecialChar(password.value)
                        )

                )

                Spacer(modifier = Modifier.height(30.dp))

                NewPasswordTextField(
                    value = confirmPassword.value,
                    onValueChange = {confirmPassword.value = it},
                    label = "Confirm Password",
                    imeAction = ImeAction.Done,
                    invalidPassword = confirmPassword.value != password.value,
                    supportingText = "Password not match"
                )

                val isLoading = confirmForgotPasswordScreenViewModel.forgotPasswordConfirm.value.loading!!
                LoadingButtonComponent(
                    text = "Submit",
                    modifier = Modifier.width(280.dp),
                    enabled = tacCode.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty() && hasMinLength(password.value) && hasUpperCase(password.value) && hasNumber(password.value) && hasSpecialChar(password.value) && password.value == confirmPassword.value && !isLoading,
                    loading = isLoading
                    ){
                    confirmForgotPasswordScreenViewModel.forgotPasswordConfirm(
                        forgotPasswordConfirmRequest = ForgotPasswordConfirmRequest(
                            confirmationCode = tacCode.value,
                            newPassword = confirmPassword.value,
                            email = if(isValidEmail(emailOrMobileNumber)) emailOrMobileNumber.trim() else null,
                            mobileNumber = if(isValidMobileNumber(emailOrMobileNumber)) emailOrMobileNumber.trim() else null,
                        ),
                        onFailure = {
                            Toast.makeText(context,"An error has occurred. Network error or Invalid tac code.",Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        navController.navigate(Login.route)
                        Toast.makeText(context,"Password successfully changed.",Toast.LENGTH_SHORT).show()
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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


@Composable
fun NewPasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    invalidPassword : Boolean,
    supportingText: String = ""
) {

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = label) },
        singleLine = true,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        supportingText = {
            if (invalidPassword) {
                Text(supportingText)
            }
        },
        isError = invalidPassword,
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val icon = if (passwordVisible.value) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = icon,
                    contentDescription = "Visibility"
                )
            }
        },
        maxLines = 1



    )
}


