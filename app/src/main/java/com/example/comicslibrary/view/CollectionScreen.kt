package com.example.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comicslibrary.model.Note
import com.example.comicslibrary.model.db.DbNote
import com.example.comicslibrary.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(
    navController: NavHostController,
    viewModel: CollectionViewModel,
    paddingValues: PaddingValues
) {

    val notes = viewModel.notes.collectAsState()
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
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(4.dp)
                    .clickable {
                        if (expanded.value == character.id) {
                            expanded.value = -1
                        } else {
                            expanded.value = character.id
                        }
                    }) {

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
                            text = title, fontWeight = FontWeight.Bold, fontSize = 22.sp
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
                        Icon(imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.deleteCharacter(character)
                            })
                        Icon(
                            imageVector = getSuitableIcon(character.id == expanded.value),
                            contentDescription = null
                        )
                    }

                }
            }

            if (character.id == expanded.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val characterNotes =
                        notes.value.filter { note -> note.characterId == character.id }
                    NotesList(characterNotes, viewModel)
                    NoteForm(character.id, viewModel)
                }
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 20.dp)
            )

        }
    }


}

@Composable
fun NoteForm(characterId: Int, viewModel: CollectionViewModel) {

    val context = LocalContext.current

    val adding = remember {
        mutableStateOf(-1)
    }
    val noteTitle = remember {
        mutableStateOf("")
    }
    val noteText = remember {
        mutableStateOf("")
    }

    if (adding.value == characterId) {
        Column(
            modifier = Modifier
                .background(Color(0x55DDE4FF))
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = noteTitle.value,
                onValueChange = { noteTitle.value = it },
                label = { Text(text = "Note title") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    value = noteText.value,
                    onValueChange = { noteText.value = it },
                    label = { Text(text = "Note text") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        if (noteTitle.value == "") {
                            Toast
                                .makeText(context, "Note title is required", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val note = Note(characterId, noteTitle.value, noteText.value)
                            viewModel.addNote(note)
                            noteTitle.value = ""
                            noteText.value = ""
                            adding.value = -1
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        }
    }

    Button(
        enabled = adding.value == -1,
        onClick = { adding.value = characterId },
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
            Text(text = "New note")
        }
    }

}

@Composable
fun NotesList(notes: List<DbNote>, viewModel: CollectionViewModel) {
    for (note in notes) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0x55DDE4FF))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = note.text
                )
            }
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.clickable {
                    viewModel.deleteNote(note)
                }
            )
        }
    }
}

fun getSuitableIcon(isExpanded: Boolean) =
    if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown