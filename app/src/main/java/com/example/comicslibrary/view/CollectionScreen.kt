package com.example.comicslibrary.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comicslibrary.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(
    navController: NavHostController,
    viewModel: CollectionViewModel,
    paddingValues: PaddingValues
) {

    val collection = viewModel.collection.collectAsState()
    val expanded = remember {
        mutableStateOf(-1)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        items(collection.value) { character ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(4.dp)
                        .clickable {
                            if (expanded.value == character.id) {
                                expanded.value = -1
                            } else {
                                expanded.value = character.id
                            }
                        })
                {

                    val imageUrl = character.thumbnail
                    val title = character.name ?: "No name"
                    val comics = character.comics ?: "No comics"

                    CharacterImage(
                        url = imageUrl,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight(),
                        contentScale = ContentScale.FillHeight
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            text = comics,
                            fontStyle = FontStyle.Italic,
                            fontSize = 8.sp,
                            maxLines = 4,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.deleteCharacter(character)
                            }
                        )
                        Icon(
                            imageVector = getSuitableIcon(character.id == expanded.value),
                            contentDescription = null
                        )
                    }

                }
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 20.dp)
            )

        }
    }


}
fun getSuitableIcon(isExpanded: Boolean) =
    if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown