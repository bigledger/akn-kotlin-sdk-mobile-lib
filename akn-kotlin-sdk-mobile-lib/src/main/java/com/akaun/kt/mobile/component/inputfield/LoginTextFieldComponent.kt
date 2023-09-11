

package com.akaun.kt.mobile.component.inputfield
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.akaun.kt.mobile.utils.isValidEmail
import com.akaun.kt.mobile.utils.isValidMobileNumber


@Composable
fun LoginTextFieldComponent(modifier : Modifier = Modifier,
                            valueState: MutableState<String> = mutableStateOf(""),
                            enabled: Boolean = true,
                            readOnly: Boolean = false,
                            label: String = "",
                            keyboardType: KeyboardType = KeyboardType.Text,
                            imeAction: ImeAction = ImeAction.Next,
                            onAction: KeyboardActions = KeyboardActions.Default,
                            supportingText: String = "") {

    val isErrorEmailOrMobile = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            isErrorEmailOrMobile.value = !(isValidEmail(valueState.value.trim()) ||
                    isValidMobileNumber(valueState.value.trim()))
                        },
        label = { Text(text = label) },
        singleLine = true,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        supportingText = { if (isErrorEmailOrMobile.value) Text(text = supportingText) },
        isError = isErrorEmailOrMobile.value
    )
}

