package com.ralphevmanzano.movies.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ralphevmanzano.movies.domain.model.Movie
import com.skydoves.sandwich.getOrThrow
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(private val service: MovieService, private val type: Movie.Type) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: 1
        try {
            val response = when (type) {
                Movie.Type.NOW_PLAYING -> service.getNowPlaying(pageIndex)
                Movie.Type.POPULAR -> service.getPopular(pageIndex)
                Movie.Type.TOP_RATED -> service.getTopRated(pageIndex)
                Movie.Type.UPCOMING -> service.getUpcoming(pageIndex)
            }

            return if (response.isSuccess) {
                val movies = response.getOrThrow().results
                val nextKey = if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
                LoadResult.Page(data = movies ?: emptyList(), prevKey = null, nextKey = nextKey)
            } else {
                LoadResult.Error(Throwable(response.messageOrNull))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 25
    }
}