package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

/**
 * Repository Interface.
 */
interface CollectionDbRepo {

    /**
     * Get all characters from repository.
     */
    suspend fun getCharacters(): Flow<List<DbCharacter>>

    /**
     * Get a character from repository by [characterId].
     */
    suspend fun getCharacter(characterId: Int): Flow<DbCharacter>

    /**
     * Add a character to repository.
     */
    suspend fun addCharacter(character: DbCharacter)

    /**
     * Update a character from repository.
     */
    suspend fun updateCharacter(character: DbCharacter)

    /**
     * Delete a character from repository.
     */
    suspend fun deleteCharacter(character: DbCharacter)

    /**
     * Delete all characters from repository.
     */
    suspend fun deleteCharacters()

    /**
     * Get all notes from repository.
     */
    suspend fun getNotes(): Flow<List<DbNote>>

    /**
     * Get all notes from a character from repository.
     */
    suspend fun getCharacterNotes(characterId: Int): Flow<List<DbNote>>

    /**
     * Get a note from repository by [noteId].
     */
    suspend fun getNote(noteId: Int): Flow<DbNote>

    /**
     * Add a note to repository.
     */
    suspend fun addNote(note: DbNote)

    /**
     * Update a note from repository.
     */
    suspend fun updateNote(note: DbNote)

    /**
     * Delete a note from repository.
     */
    suspend fun deleteNote(note: DbNote)

    /**
     * Delete all notes from a character from repository.
     */
    suspend fun deleteCharacterNotes(characterId: Int)

    /**
     * Delete all notes from repository.
     */
    suspend fun deleteNotes()

}