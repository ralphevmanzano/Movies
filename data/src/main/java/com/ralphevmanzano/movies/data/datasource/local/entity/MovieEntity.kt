package com.ralphevmanzano.movies.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ralphevmanzano.movies.domain.model.Movie

@Entity(tableName = "favourites", indices = [Index(value = ["id"], unique = true)])
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("poster_path")
    val posterPath: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("vote_average")
    val voteAverage: Float,
    @ColumnInfo("vote_count")
    val voteCount: Int
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            genreIds = genreIds,
            overview = overview,
            posterPath = posterPath,
            title = title,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}