package com.ralphevmanzano.movies.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ralphevmanzano.movies.data.datasource.remote.MovieApiKeyInterceptor
import com.ralphevmanzano.movies.data.datasource.remote.MovieService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(MovieApiKeyInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
    }

    @Provides
    fun provideMovieService(retrofitBuilder: Retrofit.Builder): MovieService {
        return retrofitBuilder.baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MovieService::class.java)
    }
}