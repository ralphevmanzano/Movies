package com.ralphevmanzano.movies.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ralphevmanzano.movies.domain.model.Movie
import com.skydoves.sandwich.getOrThrow

class MoviesPagingSource(
    private val service: MovieService,
    private val type: Movie.Type?,
    private val query: String = ""
) : PagingSource<Int, Pair<Movie, Int>>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<Movie, Int>> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = when (type) {
                Movie.Type.NOW_PLAYING -> service.getNowPlaying(pageIndex)
                Movie.Type.POPULAR -> service.getPopular(pageIndex)
                Movie.Type.TOP_RATED -> service.getTopRated(pageIndex)
                Movie.Type.UPCOMING -> service.getUpcoming(pageIndex)
                else -> service.searchMovies(query, pageIndex)
            }
            val movies = response.getOrThrow().results
            val results = response.getOrThrow().totalResults
            movies.forEach {
                it.totalResults = results
            }
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                pageIndex + 1
            }

            LoadResult.Page(
                data = movies.map { Pair(it, results) },
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<Movie, Int>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        private const val STARTING_PAGE_INDEX = 1
    }
}