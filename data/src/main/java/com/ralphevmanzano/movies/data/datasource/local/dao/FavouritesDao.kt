package com.ralphevmanzano.movies.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphevmanzano.movies.data.datasource.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites")
    fun loadFavourites(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(movie: MovieEntity)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun removeFavourite(id: Int)
}