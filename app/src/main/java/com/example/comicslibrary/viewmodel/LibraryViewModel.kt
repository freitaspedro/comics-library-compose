package com.example.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.api.MarvelApiRepo
import com.example.comicslibrary.model.conn.ConnMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repo: MarvelApiRepo,
    connMonitor: ConnMonitor
): ViewModel() {

    val result = repo.characters
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)
    val character = repo.character
    val networkAvailable = connMonitor

    init {
        retrieveCharacters()
    }

    private fun retrieveCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { validateQuery(it) }
                .debounce(1000)
                .collect {
                    repo.query(it)
                }
        }
    }

    private fun validateQuery(query: String): Boolean = query.length >= 2

    fun onQueryUpdate(input: String) {
        queryText.value = input
        queryInput.trySend(input)
    }

    fun retrieveCharacter(id: Int) {
        repo.getCharacter(id)
    }
}