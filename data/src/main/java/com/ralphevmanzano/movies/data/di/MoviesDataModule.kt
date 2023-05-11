package com.ralphevmanzano.movies.data.di

import android.content.Context
import androidx.room.Room
import com.ralphevmanzano.movies.data.datasource.local.MoviesDatabase
import com.ralphevmanzano.movies.data.datasource.local.MoviesLocalDataSourceImpl
import com.ralphevmanzano.movies.data.datasource.local.dao.FavouritesDao
import com.ralphevmanzano.movies.data.datasource.local.dao.GenresDao
import com.ralphevmanzano.movies.domain.datasource.MoviesLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesDataModule {

    private companion object {
        private val FAVOURITES_DB = "favourites.db"
    }

    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        val factory = SupportFactory(SQLiteDatabase.getBytes("passphrase".toCharArray()))
        return Room.databaseBuilder(context, MoviesDatabase::class.java, FAVOURITES_DB)
            .openHelperFactory(factory).build()
    }

    @Provides
    @Singleton
    fun provideFavouritesDao(moviesDatabase: MoviesDatabase) =
        moviesDatabase.favouritesDao()

    @Provides
    @Singleton
    fun provideGenresDao(moviesDatabase: MoviesDatabase) = moviesDatabase.genresDao()

    @Provides
    @Singleton
    fun provideMoviesLocalDataSource(
        favouritesDao: FavouritesDao,
        genresDao: GenresDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): MoviesLocalDataSource {
        return MoviesLocalDataSourceImpl(favouritesDao, genresDao, dispatcher)
    }
}