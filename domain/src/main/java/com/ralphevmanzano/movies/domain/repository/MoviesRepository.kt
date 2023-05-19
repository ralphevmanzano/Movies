package com.ralphevmanzano.movies.domain.repository

import androidx.paging.PagingData
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    // Remote
    fun getNowPlaying(): Flow<PagingData<Pair<Movie, Int>>>
    fun getPopular(): Flow<PagingData<Pair<Movie, Int>>>
    fun getTopRated(): Flow<PagingData<Pair<Movie, Int>>>
    fun getUpcoming(): Flow<PagingData<Pair<Movie, Int>>>
    fun searchMovies(query: String): Flow<PagingData<Pair<Movie, Int>>>
    fun getDetails(id: Int): Flow<Result<Movie>>
    fun getGenres(): Flow<Result<Boolean>>

    // Local
    fun loadFavourites(): Flow<List<Movie>>
    suspend fun addFavourite(movie: Movie)
    suspend fun removeFavourite(id: Int)
    suspend fun loadGenres(): List<Genre>
}