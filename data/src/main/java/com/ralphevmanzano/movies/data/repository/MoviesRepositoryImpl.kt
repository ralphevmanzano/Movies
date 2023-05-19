package com.ralphevmanzano.movies.data.repository

import androidx.paging.PagingData
import com.ralphevmanzano.movies.domain.datasource.MoviesLocalDataSource
import com.ralphevmanzano.movies.domain.datasource.MoviesRemoteDataSource
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val local: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource
) : MoviesRepository {

    override fun getNowPlaying() = remote.getNowPlaying()

    override fun getPopular() = remote.getPopular()

    override fun getTopRated() = remote.getTopRated()

    override fun getUpcoming() = remote.getUpcoming()
    override fun searchMovies(query: String) = remote.searchMovies(query)

    override fun getDetails(id: Int) = remote.getDetails(id)
    override fun getGenres(): Flow<Result<Boolean>> {
        return flow {
            val localGenres = local.loadGenres()
            if (localGenres.isNotEmpty()) {
                emit(Result.success(true))
                return@flow
            }
            remote.getGenres().onEach { result ->
                if (result.status == Result.Status.SUCCESS) {
                    result.data?.let {
                        local.addGenres(it)
                        emit(Result.success(true))
                        return@onEach
                    }
                }
                emit(Result.error(result.message.orEmpty(), null, null))
            }.collect()
        }
    }

    override fun loadFavourites() = local.loadFavourites()

    override suspend fun addFavourite(movie: Movie) {
        local.addFavourite(movie.copy(genreIds = movie.genres.map { it.id }))
    }

    override suspend fun removeFavourite(id: Int) {
        local.removeFavourite(id)
    }

    override suspend fun loadGenres(): List<Genre> {
        return local.loadGenres()
    }
}