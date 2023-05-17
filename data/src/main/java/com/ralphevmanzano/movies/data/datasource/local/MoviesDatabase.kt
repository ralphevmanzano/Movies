package com.ralphevmanzano.movies.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ralphevmanzano.movies.data.datasource.local.converters.MoviesTypeConverters
import com.ralphevmanzano.movies.data.datasource.local.dao.FavouritesDao
import com.ralphevmanzano.movies.data.datasource.local.dao.GenresDao
import com.ralphevmanzano.movies.data.datasource.local.entity.GenreEntity
import com.ralphevmanzano.movies.data.datasource.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1)
@TypeConverters(MoviesTypeConverters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun genresDao(): GenresDao
}