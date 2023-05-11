package com.ralphevmanzano.movies.data.datasource.remote.model

import com.ralphevmanzano.movies.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGenresResponse(
    @SerialName("genres")
    val genres: List<Genre> = emptyList()
)
