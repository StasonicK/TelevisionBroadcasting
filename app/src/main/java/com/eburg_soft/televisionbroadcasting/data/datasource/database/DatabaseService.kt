package com.eburg_soft.televisionbroadcasting.data.datasource.database

import android.content.Context
import androidx.room.Room

object DatabaseService {

    private const val DATABASE_NAME = "TV.db"

    fun createDatabase(applicationContext: Context): TVDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TVDatabase::class.java,
            DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }
}