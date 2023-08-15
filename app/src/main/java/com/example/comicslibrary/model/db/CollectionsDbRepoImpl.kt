package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionsDbRepoImpl(private val characterDao: CharacterDao): CollectionsDbRepo {

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

}