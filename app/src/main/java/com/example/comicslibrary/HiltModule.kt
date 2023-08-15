package com.example.comicslibrary

import android.content.Context
import androidx.room.Room
import com.example.comicslibrary.model.api.ApiService
import com.example.comicslibrary.model.api.MarvelApiRepo
import com.example.comicslibrary.model.db.CharacterDao
import com.example.comicslibrary.model.db.CollectionsDb
import com.example.comicslibrary.model.db.CollectionsDbRepo
import com.example.comicslibrary.model.db.CollectionsDbRepoImpl
import com.example.comicslibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionsDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionsDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionsDb: CollectionsDb) = collectionsDb.characterDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao): CollectionsDbRepo =
        CollectionsDbRepoImpl(characterDao)

}