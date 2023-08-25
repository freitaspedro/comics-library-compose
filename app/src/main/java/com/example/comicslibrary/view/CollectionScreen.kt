package com.example.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    val expanded = remember { mutableStateOf(-1) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        items(collection.value) { character ->
            val imageUrl = character.thumbnail
            val title = character.name ?: "No name"
            val comics = character.comics ?: "No comics"

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
                            style = TextStyle.BoldMedium
                        )
                        Text(
                            text = comics,
                            style = TextStyle.ItalicSmallMinus,
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
                        IconDelete(
                            modifier = Modifier.clickable {
                                viewModel.deleteCharacter(character)
                            }
                        )
                        if (character.id == expanded.value)
                            IconArrowUp(modifier = Modifier)
                        else
                            IconArrowDown(modifier = Modifier)
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

    val adding = remember { mutableStateOf(-1) }
    val noteTitle = remember { mutableStateOf("") }
    val noteText = remember { mutableStateOf("") }

    if (adding.value == characterId) {
        Column(
            modifier = Modifier
                .background(Color(0x55DDE4FF))
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextFieldCommon(
                modifier = Modifier,
                text = noteTitle,
                label = { Text(text = "Note title") },
                placeholder = null
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                TextFieldCommon(
                    modifier = Modifier,
                    text = noteText,
                    label = { Text(text = "Note text") },
                    placeholder = null
                )

                IconCheck(
                    modifier = Modifier.clickable {
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
                )

            }
        }
    }

    IconButton(
        enabled = adding.value == -1,
        onClick = { adding.value = characterId },
        iconEnabled = { IconAdd(modifier = Modifier) },
        textEnabled = { Text(text = "New note") }
    )

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
                    style = TextStyle.BoldSmall
                )
                Text(
                    text = note.text,
                    style = TextStyle.Small
                )
            }
            IconDelete(
                modifier = Modifier.clickable {
                    viewModel.deleteNote(note)
                }
            )
        }
    }
}
