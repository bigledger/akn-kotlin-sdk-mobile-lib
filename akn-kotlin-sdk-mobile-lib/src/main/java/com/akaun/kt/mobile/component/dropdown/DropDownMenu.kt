package com.akaun.kt.mobile.component.dropdown

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties


private val dropdownMenuVerticalPadding = 8.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchableMapDropDown(
    modifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier,
    outerModifier: Modifier = Modifier,
    label: String,
    enable: Boolean = true,
    initialValue : String = "",
    readOnly: Boolean = true,
    placeholder: String = "Select Option",
    searchPlaceholder: String = "Select Option",
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onDropDownItemSelected: (String, String) -> Unit = { _, _ -> },
    dropdownItem: @Composable (String) -> Unit,
    isError: Boolean = false,
    map: MutableMap<String, String>
) {
    var selectedOptionText by rememberSaveable { mutableStateOf(initialValue) }
    var searchedOption by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var filteredItems = mutableMapOf<String, String>()
    val keyboardController = LocalSoftwareKeyboardController.current


    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != map.values.indices.toSet()) {
            // if we don't have all heights calculated yet, return default value
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        // top+bottom system padding
        var sum = with(density) { dropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((_, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        // all items fit into base height
        baseHeight
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = outerModifier,
            colors = colors,
            label = { Text(text = label) },
            value = selectedOptionText,
            readOnly = readOnly,
            enabled = enable,
            onValueChange = { selectedOptionText = it },
            placeholder = {
                Text(text = placeholder)
            },
            trailingIcon = {
                IconToggleButton(
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    }
                ) {
                    if (expanded) Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        Modifier.rotate(180f)
                    ) else Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            isError = isError,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded  = !expanded
                            }
                        }
                    }
                }
        )
        if (expanded) {
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .requiredSizeIn(maxHeight = maxHeight),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        modifier = innerModifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = searchedOption,
                        onValueChange = { selectedItem ->
                            searchedOption = selectedItem
                            filteredItems = map.filterValues {
                                it.contains(
                                    searchedOption,
                                    ignoreCase = true
                                )
                            }.toMutableMap()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        },
                        placeholder = {
                            Text(text = searchPlaceholder)
                        }
                    )

                    val items = if (filteredItems.isEmpty()) {
                        map
                    } else {
                        filteredItems
                    }

                    items.forEach {(key, value) ->
                        DropdownMenuItem(
                            onClick = {
                                keyboardController?.hide()
                                selectedOptionText = value.toString()
                                onDropDownItemSelected(key, value)
                                searchedOption = ""
                                expanded = false
                            },
                            text = {
                                dropdownItem(value)
                            },
                            colors = MenuDefaults.itemColors()
                        )

                    }
                }
            }
        }
    }
}

@Deprecated("Not in use anymore, faulty.")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDropDown(label: String,
                  value: String,
                  dropDownItemList: List<String>,
                  onClick: (String) -> Unit
                  ) {
    val expanded = rememberSaveable{ mutableStateOf(false) }
    val item = rememberSaveable { mutableStateOf(value) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }) {
        OutlinedTextField(
            label = { Text(text = label) },
            value = item.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(10.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {
            for (dropDownItem in dropDownItemList) {
                DropdownMenuItem(
                    text = { Text(text = dropDownItem) },
                    onClick = {
                        onClick(dropDownItem)
                        expanded.value = false
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    value: String = "",
    dropDownItemList: List<String>,
    onClick: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf(value) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = expanded,
            onExpandedChange = {
                if (enabled) {
                    expanded = !expanded
                }
            }
        ) {
            OutlinedTextField(
                label = { Text(text = label) },
                value = selectedText,
                onValueChange = {},
                enabled = enabled,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
            ) {
                dropDownItemList.forEach { item ->
                    DropdownMenuItem(
                        modifier = modifier,
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onClick(item)
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    modifier: Modifier = Modifier,
    label: String,
    enabled: MutableState<Boolean>,
    value: String = "",
    dropDownItemList: List<String>,
    onClick: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf(value) }

    // Reset the value when disabled.
    if (!enabled.value) {
        selectedText = ""
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = expanded,
            onExpandedChange = {
                if (enabled.value) {
                    expanded = ! expanded
                }
            }
        ) {
            OutlinedTextField(
                label = { Text(text = label) },
                value = selectedText,
                onValueChange = {},
                enabled = enabled.value,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
            ) {
                dropDownItemList.forEach { item ->
                    DropdownMenuItem(
                        modifier = modifier,
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onClick(item)
                        }
                    )
                }
            }
        }
    }
}