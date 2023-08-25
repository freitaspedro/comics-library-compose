package com.example.comicslibrary.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun IconButton(
    enabled: Boolean,
    onClick: () -> Unit,
    iconEnabled: @Composable () -> Unit,
    textEnabled: @Composable () -> Unit,
    iconDisabled: @Composable (() -> Unit)? = null,
    textDisabled: @Composable (() -> Unit)? = null,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (enabled) {
                iconEnabled()
                textEnabled()
            } else {
                if (iconDisabled != null && textDisabled != null) {
                    iconDisabled()
                    textDisabled()
                } else {
                    iconEnabled()
                    textEnabled()
                }
            }
        }
    }
}