package com.example.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.CharacterResult
import com.example.comicslibrary.model.db.CollectionDbRepo
import com.example.comicslibrary.model.db.DbCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val repo: CollectionDbRepo): ViewModel() {

    val character = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())

    init {
        retrieveCollection()
    }

    private fun retrieveCollection() {
        viewModelScope.launch {
            repo.getCharacters().collect {
                collection.value = it
            }
        }
    }

    fun setCharacter(characterId: Int?) {
        characterId?.let {
            viewModelScope.launch {
                repo.getCharacter(it).collect {
                    character.value = it
                }
            }
        }
    }

    fun addCharacter(character: CharacterResult) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addCharacter(DbCharacter.fromCharacter(character))
        }
    }

    fun deleteCharacter(character: DbCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteCharacter(character)
        }
    }

}