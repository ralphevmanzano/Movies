package com.ralphevmanzano.movies.data.di

import android.content.Context
import androidx.room.Room
import com.ralphevmanzano.movies.data.datasource.local.MoviesDatabase
import com.ralphevmanzano.movies.data.datasource.local.dao.FavouritesDao
import com.ralphevmanzano.movies.data.datasource.local.dao.GenresDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private companion object {
        private val FAVOURITES_DB = "favourites.db"
    }

    @Provides
    fun provideFavouritesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        val factory = SupportFactory(SQLiteDatabase.getBytes("passphrase".toCharArray()))
        return Room.databaseBuilder(context, MoviesDatabase::class.java, FAVOURITES_DB)
            .openHelperFactory(factory).build()
    }

    @Provides
    fun provideFavouritesDao(moviesDatabase: MoviesDatabase): FavouritesDao =
        moviesDatabase.favouritesDao()

    @Provides
    fun provideGenresDao(moviesDatabase: MoviesDatabase): GenresDao = moviesDatabase.genresDao()

}