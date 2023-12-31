package com.example.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comicslibrary.Destination
import com.example.comicslibrary.model.CharactersApiResponse
import com.example.comicslibrary.model.api.NetworkResult
import com.example.comicslibrary.model.conn.ConnObservable
import com.example.comicslibrary.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {

    val result by viewModel.result.collectAsState()
    val searchText = viewModel.queryText.collectAsState()
    val networkAvailable =
        viewModel.networkAvailable.observe().collectAsState(
            initial = ConnObservable.Status.Available
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (networkAvailable.value == ConnObservable.Status.Unavailable) NetworkUnavailable()

        SearchTextField(
            modifier = Modifier.padding(8.dp),
            value = searchText.value,
            onValueChange = viewModel::onQueryUpdate,
            label = { Text(text = "Character search") },
            placeholder = { Text(text = "Character") }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character", style = TextStyle.Medium)
                }
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResult.Success -> {
                    DisplayCharacters(result, navController)
                }
                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}", style = TextStyle.Medium)
                }
            }

        }


    }
}

@Composable
fun NetworkUnavailable() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Network unavailable",
            color = Color.White,
            style = TextStyle.Small,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
fun DisplayCharacters(
    result: NetworkResult<CharactersApiResponse>,
    navController: NavHostController
) {

    val backgrounColor = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else Color.LightGray

    result.data?.data?.results?.let { characters ->
        LazyColumn(
            modifier = Modifier.background(backgrounColor),
            verticalArrangement = Arrangement.Top
        ) {

            result.data.attributionText?.let {
                item {
                    Text(
                        text = it,
                        style = TextStyle.Small,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            items(characters) { character ->
                val imageUrl = character.thumbnail?.path + "." + character.thumbnail?.extension
                val title = character.name ?: "No name"
                val description = character.description ?: "No description"
                val context = LocalContext.current

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colors.background)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            if (character.id != null)
                                navController.navigate(
                                    Destination.CharacterDetails.createRoute(
                                        character.id
                                    )
                                )
                            else
                                Toast
                                    .makeText(context, "Character id is null", Toast.LENGTH_SHORT)
                                    .show()
                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CharacterImage(
                            url = imageUrl,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)
                        )
                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(
                                text = title,
                                style = TextStyle.BoldMedium
                            )
                            Text(
                                text = description,
                                style = TextStyle.Small,
                                maxLines = 4
                            )
                        }
                    }


                }

            }
        }
    }
}
