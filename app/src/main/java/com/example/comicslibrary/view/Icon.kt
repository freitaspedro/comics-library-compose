package com.example.comicslibrary.view

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun IconCheck(modifier: Modifier) = IconSuper(image = Icons.Default.Check, modifier = modifier)

@Composable
fun IconAdd(modifier: Modifier) = IconSuper(image = Icons.Default.Add, modifier = modifier)

@Composable
fun IconDelete(modifier: Modifier) = IconSuper(image = Icons.Outlined.Delete, modifier = modifier)

@Composable
fun IconArrowDown(modifier: Modifier) =
    IconSuper(image = Icons.Outlined.KeyboardArrowDown, modifier = modifier)

@Composable
fun IconArrowUp(modifier: Modifier) =
    IconSuper(image = Icons.Outlined.KeyboardArrowUp, modifier = modifier)

@Composable
fun IconSuper(image: ImageVector, modifier: Modifier) {
    Icon(
        imageVector = image,
        contentDescription = null,
        modifier = modifier
    )
}

