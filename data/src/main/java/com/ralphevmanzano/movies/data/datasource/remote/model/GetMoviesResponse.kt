package com.ralphevmanzano.movies.data.datasource.remote.model

import com.ralphevmanzano.movies.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMoviesResponse(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val results: List<Movie> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0
)