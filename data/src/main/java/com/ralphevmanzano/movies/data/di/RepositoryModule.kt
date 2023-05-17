package com.ralphevmanzano.movies.data.di

import com.ralphevmanzano.movies.data.datasource.local.MoviesLocalDataSourceImpl
import com.ralphevmanzano.movies.data.datasource.local.dao.FavouritesDao
import com.ralphevmanzano.movies.data.datasource.local.dao.GenresDao
import com.ralphevmanzano.movies.data.datasource.remote.MovieService
import com.ralphevmanzano.movies.data.datasource.remote.MoviesRemoteDataSourceImpl
import com.ralphevmanzano.movies.data.repository.MoviesRepositoryImpl
import com.ralphevmanzano.movies.domain.datasource.MoviesLocalDataSource
import com.ralphevmanzano.movies.domain.datasource.MoviesRemoteDataSource
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideMoviesLocalDataSource(
        favouritesDao: FavouritesDao,
        genresDao: GenresDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): MoviesLocalDataSource {
        return MoviesLocalDataSourceImpl(favouritesDao, genresDao, dispatcher)
    }

    @Provides
    fun provideMoviesRemoteDataSource(
        service: MovieService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): MoviesRemoteDataSource {
        return MoviesRemoteDataSourceImpl(service, dispatcher)
    }

    @Provides
    fun provideMoviesRepository(
        localDataSource: MoviesLocalDataSource,
        remoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(localDataSource, remoteDataSource)
    }
}