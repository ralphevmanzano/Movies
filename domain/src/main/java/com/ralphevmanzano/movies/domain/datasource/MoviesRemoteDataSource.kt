package com.ralphevmanzano.movies.domain.datasource

import androidx.paging.PagingData
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {
    fun getNowPlaying(): Flow<PagingData<Movie>>
    fun getPopular(): Flow<PagingData<Movie>>
    fun getTopRated(): Flow<PagingData<Movie>>
    fun getUpcoming(): Flow<PagingData<Movie>>
    fun getDetails(id: Int): Flow<Result<Movie>>
    fun getGenres(): Flow<Result<List<Genre>>>
}