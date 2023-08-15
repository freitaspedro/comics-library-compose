package com.example.comicslibrary.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comicslibrary.Destination
import com.example.comicslibrary.model.db.comicsToString
import com.example.comicslibrary.viewmodel.LibraryViewModel

@Composable
fun CharacterDetailsScreen(
    navController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {

    val character = viewModel.character.value

    if (character == null) {
        navController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(
                start = 4.dp,
                top = 12.dp,
                end = 4.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = character?.thumbnail?.path + "." + character?.thumbnail?.extension
        val title = character?.name ?: "No name"
        val comics = character?.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "No comics"
        val description = character?.description ?: "No description"

        CharacterImage(
            url = imageUrl,
            modifier = Modifier
                .width(200.dp)
                .padding(22.dp)
        )

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = comics,
            fontStyle = FontStyle.Italic,
            fontSize = 8.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.padding(22.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
                Text(
                    text = "Add to collection"
                )
            }
        }

    }

}