package com.ralphevmanzano.movies.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    fun getNowPlaying(): Flow<PagingData<Movie>> {
        return repository.getNowPlaying().cachedIn(viewModelScope)
    }

    fun getPopular(): Flow<PagingData<Movie>> {
        return repository.getPopular().cachedIn(viewModelScope)
    }

    fun getTopRated(): Flow<PagingData<Movie>> {
        return repository.getTopRated().cachedIn(viewModelScope)
    }

    fun getUpcoming(): Flow<PagingData<Movie>> {
        return repository.getUpcoming().cachedIn(viewModelScope)
    }
}