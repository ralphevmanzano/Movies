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
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = when (type) {
                Movie.Type.NOW_PLAYING -> service.getNowPlaying(pageIndex)
                Movie.Type.POPULAR -> service.getPopular(pageIndex)
                Movie.Type.TOP_RATED -> service.getTopRated(pageIndex)
                Movie.Type.UPCOMING -> service.getUpcoming(pageIndex)
            }
            val movies = response.getOrThrow().results
            val nextKey = if (movies.isEmpty()) { null } else { pageIndex + 1 }

            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
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