package com.jskaleel.fte.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jskaleel.fte.data.source.local.entities.ELocalBooks
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalBooksDao {
    @Query("SELECT * from localBooks")
    fun getBooks(): Flow<List<ELocalBooks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localBooks: List<ELocalBooks>)

    @Query("DELETE from localBooks")
    fun deleteAll()
}