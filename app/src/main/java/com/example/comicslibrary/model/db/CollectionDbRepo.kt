package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {

    suspend fun getCharacters(): Flow<List<DbCharacter>>

    suspend fun getCharacter(characterId: Int): Flow<DbCharacter>

    suspend fun addCharacter(character: DbCharacter)

    suspend fun updateCharacter(character: DbCharacter)

    suspend fun deleteCharacter(character: DbCharacter)

}