package com.example.comicslibrary.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comicslibrary.Destination
import com.example.comicslibrary.model.CharacterResult
import com.example.comicslibrary.model.db.comicsToString
import com.example.comicslibrary.viewmodel.CollectionViewModel
import com.example.comicslibrary.viewmodel.LibraryViewModel

@Composable
fun CharacterDetailsScreen(
    navController: NavHostController,
    libraryViewModel: LibraryViewModel,
    collectionViewModel: CollectionViewModel,
    paddingValues: PaddingValues
) {

    val character = libraryViewModel.character.value
    val collection by collectionViewModel.collection.collectAsState()
    val isInCollection = collection.map { it.apiId }.contains(character?.id)

    if (character == null) {
        navController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        collectionViewModel.setCharacter(character?.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 6.dp,
                top = 12.dp,
                end = 6.dp,
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
            style = TextStyle.BoldLarge,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = comics,
            style = TextStyle.ItalicSmall,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = description,
            style = TextStyle.Medium,
            modifier = Modifier.padding(22.dp)
        )

        IconButton(
            enabled = !isInCollection,
            onClick = { if (!isInCollection) collectionViewModel.suitableSave(character) },
            primaryIcon = { IconAdd(modifier = Modifier) },
            primaryText = { Text(text = "New note") },
            secondIcon = { IconCheck(modifier = Modifier) },
            secondText = { Text(text = "Added") }
        )

    }

}

fun CollectionViewModel.suitableSave(c: CharacterResult?) = c?.let { this.addCharacter(it) }
