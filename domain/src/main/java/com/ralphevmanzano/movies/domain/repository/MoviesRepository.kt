package com.ralphevmanzano.movies.domain.repository

import androidx.paging.PagingData
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    // Remote
    fun getNowPlaying(): Flow<PagingData<Movie>>
    fun getPopular(): Flow<PagingData<Movie>>
    fun getTopRated(): Flow<PagingData<Movie>>
    fun getUpcoming(): Flow<PagingData<Movie>>
    fun getDetails(id: Int): Flow<Result<Movie>>

    // Local
    fun loadFavourites(): Flow<List<Movie>>
    suspend fun addFavourite(movie: Movie)
    suspend fun removeFavourite(id: Int)
}