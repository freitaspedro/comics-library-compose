package com.example.comicslibrary.model.conn

import kotlinx.coroutines.flow.Flow

interface ConnObservable {

    fun observe(): Flow<Status>

    enum class Status {
        Available,
        Unavailable
    }

}