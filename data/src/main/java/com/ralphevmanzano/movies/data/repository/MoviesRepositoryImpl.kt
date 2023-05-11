package com.ralphevmanzano.movies.data.repository

import com.ralphevmanzano.movies.domain.datasource.MoviesLocalDataSource
import com.ralphevmanzano.movies.domain.datasource.MoviesRemoteDataSource
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val local: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource
) : MoviesRepository {

    override fun getNowPlaying() = remote.getNowPlaying()

    override fun getPopular() = remote.getPopular()

    override fun getTopRated() = remote.getTopRated()

    override fun getUpcoming() = remote.getUpcoming()

    override fun getDetails(id: Int) = remote.getDetails(id)

    override fun loadFavourites() = local.loadFavourites()

    override suspend fun addFavourite(movie: Movie) {
        local.addFavourite(movie)
    }

    override suspend fun removeFavourite(id: Int) {
        local.removeFavourite(id)
    }
}