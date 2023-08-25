package com.example.comicslibrary.view

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun SearchTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun TextFieldCommon(
    modifier: Modifier,
    text: MutableState<String>,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?
) {
    OutlinedTextField(
        modifier = modifier,
        value = text.value,
        onValueChange = { text.value = it },
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}