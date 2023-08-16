package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(
    private val characterDao: CharacterDao,
    private val noteDao: NoteDao
) : CollectionDbRepo {

    override suspend fun getCharacters(): Flow<List<DbCharacter>> =
        characterDao.getCharacters()

    override suspend fun getCharacter(characterId: Int): Flow<DbCharacter> =
        characterDao.getCharacter(characterId)

    override suspend fun addCharacter(character: DbCharacter) =
        characterDao.addCharacter(character)

    override suspend fun updateCharacter(character: DbCharacter) =
        characterDao.updateCharacter(character)

    override suspend fun deleteCharacter(character: DbCharacter) =
        characterDao.deleteCharacter(character)

    override suspend fun deleteCharacters() =
        characterDao.deleteCharacters()

    override suspend fun getNotes(): Flow<List<DbNote>> =
        noteDao.getNotes()

    override suspend fun getCharacterNotes(characterId: Int): Flow<List<DbNote>> =
        noteDao.getCharacterNotes(characterId)

    override suspend fun getNote(noteId: Int): Flow<DbNote> =
        noteDao.getNote(noteId)

    override suspend fun addNote(note: DbNote) =
        noteDao.addNote(note)

    override suspend fun updateNote(note: DbNote) =
        noteDao.updateNote(note)

    override suspend fun deleteNote(note: DbNote) =
        noteDao.deleteNote(note)

    override suspend fun deleteCharacterNotes(characterId: Int) =
        noteDao.deleteCharacterNotes(characterId)

    override suspend fun deleteNotes() =
        noteDao.deleteNotes()

}