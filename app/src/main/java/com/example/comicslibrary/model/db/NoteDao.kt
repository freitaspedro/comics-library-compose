package com.example.comicslibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.comicslibrary.model.db.Constants.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY id")
    fun getNotes(): Flow<List<DbNote>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE characterId = :characterId ORDER BY id ASC")
    fun getCharacterNotes(characterId: Int): Flow<List<DbNote>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id = :noteId")
    fun getNote(noteId: Int): Flow<DbNote>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: DbNote)

    @Update
    fun updateNote(note: DbNote)

    @Delete
    fun deleteNote(note: DbNote)

    @Query("DELETE FROM $NOTE_TABLE WHERE characterId = :characterId")
    fun deleteCharacterNotes(characterId: Int)

    @Query("DELETE FROM $NOTE_TABLE")
    fun deleteNotes()

}