package com.jskaleel.fte.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jskaleel.fte.data.source.local.converters.ImageTypeConverter
import com.jskaleel.fte.data.source.local.dao.LocalBooksDao
import com.jskaleel.fte.data.source.local.dao.SavedBooksDao
import com.jskaleel.fte.data.source.local.entities.ELocalBooks
import com.jskaleel.fte.data.source.local.entities.ESavedBooks

@Database(entities = [ELocalBooks::class, ESavedBooks::class], version = 1, exportSchema = false)
@TypeConverters(value = [ImageTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLocalBooks(): LocalBooksDao
    abstract fun getSavedBooks(): SavedBooksDao
}