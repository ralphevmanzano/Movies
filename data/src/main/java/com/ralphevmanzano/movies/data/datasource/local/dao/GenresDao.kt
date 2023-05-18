package com.ralphevmanzano.movies.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphevmanzano.movies.data.datasource.local.entity.GenreEntity

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<GenreEntity>
}