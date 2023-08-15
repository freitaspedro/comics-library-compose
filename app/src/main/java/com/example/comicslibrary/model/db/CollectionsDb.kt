package com.example.comicslibrary.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCharacter::class], version = 1, exportSchema = false)
abstract class CollectionsDb: RoomDatabase() {

    abstract fun characterDao(): CharacterDao

}