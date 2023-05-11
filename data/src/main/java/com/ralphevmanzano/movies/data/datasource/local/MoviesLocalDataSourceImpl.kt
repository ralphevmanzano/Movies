package com.ralphevmanzano.movies.data.datasource.local

import com.ralphevmanzano.movies.data.datasource.local.dao.FavouritesDao
import com.ralphevmanzano.movies.data.datasource.local.dao.GenresDao
import com.ralphevmanzano.movies.data.datasource.local.entity.GenreEntity
import com.ralphevmanzano.movies.data.datasource.local.entity.MovieEntity
import com.ralphevmanzano.movies.domain.datasource.MoviesLocalDataSource
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesLocalDataSourceImpl(
    private val favouritesDao: FavouritesDao,
    private val genresDao: GenresDao,
    private val dispatcher: CoroutineDispatcher
) : MoviesLocalDataSource {

    override fun loadFavourites(): Flow<List<Movie>> {
        return favouritesDao.loadFavourites().flowOn(dispatcher)
            .map { entity -> entity.map { it.toMovie() } }
    }

    override suspend fun addFavourite(movie: Movie) = withContext(dispatcher) {
        favouritesDao.addFavourite(
            MovieEntity(
                id = movie.id,
                genreIds = movie.genreIds,
                overview = movie.overview,
                posterPath = movie.posterPath,
                title = movie.title,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount
            )
        )
    }

    override suspend fun removeFavourite(id: Int) = withContext(dispatcher) {
        favouritesDao.removeFavourite(id)
    }

    override suspend fun addGenres(genres: List<Genre>) = withContext(dispatcher) {
        genresDao.addGenres(genres.map { GenreEntity(it.id, it.name) })
    }

    override suspend fun getGenresByIds(ids: List<Int>) {
        genresDao.getGenresFromIds(ids)
    }
}