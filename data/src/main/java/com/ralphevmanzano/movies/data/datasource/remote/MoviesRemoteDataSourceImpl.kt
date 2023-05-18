package com.ralphevmanzano.movies.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ralphevmanzano.movies.domain.datasource.MoviesRemoteDataSource
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService,
    private val dispatcher: CoroutineDispatcher
) : MoviesRemoteDataSource {

    override fun getNowPlaying(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service, Movie.Type.NOW_PLAYING)
            }
        ).flow.flowOn(dispatcher)
    }

    override fun getPopular(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service, Movie.Type.POPULAR)
            }
        ).flow.flowOn(dispatcher)
    }

    override fun getTopRated(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service, Movie.Type.TOP_RATED)
            }
        ).flow.flowOn(dispatcher)
    }

    override fun getUpcoming(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service, Movie.Type.UPCOMING)
            }
        ).flow.flowOn(dispatcher)
    }

    override fun getDetails(id: Int): Flow<Result<Movie>> {
        return flow<Result<Movie>> {
            emit(Result.loading())
            service.getMovieDetails(id).suspendOnSuccess {
                emit(Result.success(data))
            }.suspendOnError {
                emit(Result.error(message()))
                Timber.e(message())
            }.suspendOnException {
                emit(Result.error(message()))
                Timber.e(message())
            }
        }.flowOn(dispatcher)
    }

    override fun getGenres(): Flow<Result<List<Genre>>> {
        return flow<Result<List<Genre>>> {
            emit(Result.loading())
            service.getGenres().suspendOnSuccess {
                emit(Result.success(data.genres))
            }.suspendOnError {
                emit(Result.error(message()))
                Timber.e(message())
            }.suspendOnException {
                emit(Result.error(message()))
                Timber.e(message())
            }
        }.flowOn(dispatcher)
    }
}