package com.akaun.kt.mobile.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.button.LoadingButtonComponent
import com.akaun.kt.mobile.component.inputfield.BasicTextFieldComponent
import com.akaun.kt.mobile.component.inputfield.LoginTextFieldComponent
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.BasicTextComponent
import com.akaun.kt.mobile.component.text.SmallTextComponent
import com.akaun.kt.mobile.component.text.TitleTextComponent
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.Login

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterScreenViewModel = viewModel()
) {
    val email = rememberSaveable { mutableStateOf("") }
    val mobileNumber = rememberSaveable { mutableStateOf("") }
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val emailChosen = rememberSaveable { mutableStateOf(true) }
    val policyChecked = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val validInputs = rememberSaveable(
        firstName.value, lastName.value, email.value, mobileNumber.value,
        emailChosen.value, policyChecked.value, viewModel.isLoading
    ) {
        mutableStateOf(
            firstName.value.trim().isNotEmpty()
                    && lastName.value.trim().isNotEmpty()
                    && policyChecked.value
                    && !viewModel.isLoading
                    && if (emailChosen.value) email.value.trim().isNotEmpty()
                    else mobileNumber.value.trim().isNotEmpty()
        )
    }

    if (viewModel.isError) {
        Toast.makeText(context, viewModel.errorMessage.ifEmpty { "Registration failed." }, Toast.LENGTH_SHORT).show()
        viewModel.resetIsError()
    }

    if (viewModel.isSuccess) {
        viewModel.resetIsSuccess()
        Toast.makeText(context, "Registration successful! Please check your email to verify.", Toast.LENGTH_LONG).show()
        navController.navigate(Login.route) {
            popUpTo(AuthGraph.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))
                LogoComponent(width = 200.dp, height = 50.dp)

                Spacer(modifier = Modifier.height(10.dp))

                TitleTextComponent(value = stringResource(id = R.string.register_new_id))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BasicTextFieldComponent(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        label = "First name*",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    BasicTextFieldComponent(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        label = "Last name*",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    BasicTextComponent(
                        value = stringResource(id = R.string.register_with),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    RadioButton(
                        selected = emailChosen.value,
                        onClick = { emailChosen.value = true },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    BasicTextComponent(
                        value = stringResource(id = R.string.email),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    RadioButton(
                        selected = !emailChosen.value,
                        onClick = { emailChosen.value = false },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    BasicTextComponent(
                        value = stringResource(id = R.string.mobile),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                if (emailChosen.value) {
                    LoginTextFieldComponent(
                        valueState = email,
                        label = stringResource(id = R.string.email),
                        supportingText = "Email is required.",
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LoginTextFieldComponent(
                        valueState = mobileNumber,
                        label = stringResource(id = R.string.mobile),
                        supportingText = "Mobile Number is required.",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row {
                    Checkbox(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = policyChecked.value,
                        onCheckedChange = { policyChecked.value = !policyChecked.value }
                    )
                    SmallTextComponent(
                        value = stringResource(id = R.string.policy_agree),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                LoadingButtonComponent(
                    text = stringResource(id = R.string.register_now),
                    enabled = validInputs.value,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (emailChosen.value) {
                        viewModel.registerWithEmail(
                            email = email.value.trim(),
                            firstName = firstName.value.trim(),
                            lastName = lastName.value.trim()
                        )
                    } else {
                        viewModel.registerWithMobileNumber(
                            mobileNumber = mobileNumber.value.trim(),
                            firstName = firstName.value.trim(),
                            lastName = lastName.value.trim()
                        )
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
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            navController.navigate(Login.route) {
                                popUpTo(AuthGraph.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }

        // Loading overlay
        if (viewModel.isLoading) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .pointerInput(Unit) { detectTapGestures { } }
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.background(Color.White, shape = CircleShape)
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
