
package com.akaun.kt.mobile.component.inputfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun BasicTextFieldComponent(
                    modifier: Modifier = Modifier,
                    value: String = "",
                    onValueChange : (String) -> Unit,
                    enabled: Boolean = true,
                    readOnly: Boolean = false,
                    label: String = "",
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default,
                    isError : Boolean = false ,
                    trailingIcon: (@Composable () -> Unit)? = null,
                    leadingIcon: @Composable (() -> Unit)? = null,
                    supportingText: @Composable (() -> Unit)? = null) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text(text = label) },
        singleLine = true,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError
    )
}

