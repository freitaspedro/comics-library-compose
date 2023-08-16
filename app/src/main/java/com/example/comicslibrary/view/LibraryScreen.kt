package com.example.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.comicslibrary.Destination
import com.example.comicslibrary.model.CharactersApiResponse
import com.example.comicslibrary.model.api.NetworkResult
import com.example.comicslibrary.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {

    val result by viewModel.result.collectAsState()
    val searchText = viewModel.queryText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = searchText.value,
            onValueChange = viewModel::onQueryUpdate,
            label = { Text(text = "Character search") },
            placeholder = { Text(text = "Character") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResult.Success -> {
                    DisplayCharacters(result, navController)
                }
                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }

        }


    }
}

@Composable
fun DisplayCharacters(
    result: NetworkResult<CharactersApiResponse>,
    navController: NavHostController
) {
    result.data?.data?.results?.let { characters ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top
        ) {

            result.data.attributionText?.let {
                item {
                    AttributionText(text = it)
                }
            }

            items(characters) { character ->
                val imageUrl = character.thumbnail?.path + "." + character.thumbnail?.extension
                val title = character.name
                val description = character.description
                val context = LocalContext.current
                val id = character.id

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            if (character.id != null)
                                navController.navigate(Destination.CharacterDetails.createRoute(id))
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
                                text = title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = description ?: "",
                                maxLines = 4,
                                fontSize = 14.sp
                            )
                        }
                    }


                }

            }
        }
    }
}

//TODO: move this to a new file
@Composable
fun AttributionText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
        fontSize = 12.sp
    )
}

//TODO: move this to a new file
@Composable
fun CharacterImage(
    url: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
