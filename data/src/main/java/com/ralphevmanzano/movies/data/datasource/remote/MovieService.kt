package com.ralphevmanzano.movies.data.datasource.remote

import com.ralphevmanzano.movies.data.datasource.remote.model.GetGenresResponse
import com.ralphevmanzano.movies.data.datasource.remote.model.GetMoviesResponse
import com.ralphevmanzano.movies.domain.model.Movie
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): ApiResponse<GetMoviesResponse>

    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int): ApiResponse<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page: Int): ApiResponse<GetMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): ApiResponse<GetMoviesResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): ApiResponse<Movie>

    @GET("genre/movie/list")
    suspend fun getGenres(): ApiResponse<GetGenresResponse>
}