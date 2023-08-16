package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {

    suspend fun getCharacters(): Flow<List<DbCharacter>>

    suspend fun getCharacter(characterId: Int): Flow<DbCharacter>

    suspend fun addCharacter(character: DbCharacter)

    suspend fun updateCharacter(character: DbCharacter)

    suspend fun deleteCharacter(character: DbCharacter)

    suspend fun deleteCharacters()

    suspend fun getNotes(): Flow<List<DbNote>>

    suspend fun getCharacterNotes(characterId: Int): Flow<List<DbNote>>

    suspend fun getNote(noteId: Int): Flow<DbNote>

    suspend fun addNote(note: DbNote)

    suspend fun updateNote(note: DbNote)

    suspend fun deleteNote(note: DbNote)

    suspend fun deleteCharacterNotes(characterId: Int)

    suspend fun deleteNotes()

}