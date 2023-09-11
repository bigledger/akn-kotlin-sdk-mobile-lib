
package com.akaun.kt.mobile.component.inputfield
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.akaun.kt.mobile.R



@Composable
fun PasswordTextFieldComponent(modifier: Modifier = Modifier,
                               passwordValueState: MutableState<String> = mutableStateOf(""),
                               enabled: Boolean = true,
                               readOnly: Boolean = false,
                               label: String = "",
                               keyboardType: KeyboardType = KeyboardType.Text,
                               imeAction: ImeAction = ImeAction.Done,
                               onAction: KeyboardActions = KeyboardActions.Default,
                               supportingText: String = "") {

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val isErrorPassword = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = passwordValueState.value,
        enabled = enabled,
        readOnly = readOnly,
        modifier = modifier,
        onValueChange = {
            passwordValueState.value = it
            isErrorPassword.value = !passwordValueState.value.trim().isNotEmpty()
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        supportingText = { if (isErrorPassword.value) Text(text = supportingText) },
        label = { Text(text = label) },
        singleLine = true,
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isErrorPassword.value
    )
}