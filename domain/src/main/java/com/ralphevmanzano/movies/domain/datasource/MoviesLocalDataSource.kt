package com.ralphevmanzano.movies.domain.datasource

import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    fun loadFavourites(): Flow<List<Movie>>
    suspend fun addFavourite(movie: Movie)
    suspend fun removeFavourite(id: Int)

    suspend fun loadGenres(): List<Genre>
    suspend fun addGenres(genres: List<Genre>)
}