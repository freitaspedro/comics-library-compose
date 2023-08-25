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
    primaryIcon: @Composable () -> Unit,
    primaryText: @Composable () -> Unit,
    secondIcon: @Composable (() -> Unit)? = null,
    secondText: @Composable (() -> Unit)? = null,
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
                primaryIcon()
                primaryText()
            } else {
                if (secondIcon != null && secondText != null) {
                    secondIcon()
                    secondText()
                } else {
                    primaryIcon()
                    primaryText()
                }
            }
        }
    }
}