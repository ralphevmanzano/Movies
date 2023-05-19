package com.ralphevmanzano.movies.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    init {
        viewModelScope.launch {
            repository.getGenres().collect { result ->
                if (result.data == true) {
                    val genres = repository.loadGenres()
                    _genres.postValue(genres)
                }
            }
        }
    }

    fun getNowPlaying(): Flow<PagingData<Pair<Movie, Int>>> {
        return repository.getNowPlaying().cachedIn(viewModelScope)
    }

    fun getPopular(): Flow<PagingData<Pair<Movie, Int>>> {
        return repository.getPopular().cachedIn(viewModelScope)
    }

    fun getTopRated(): Flow<PagingData<Pair<Movie, Int>>> {
        return repository.getTopRated().cachedIn(viewModelScope)
    }

    fun getUpcoming(): Flow<PagingData<Pair<Movie, Int>>> {
        return repository.getUpcoming().cachedIn(viewModelScope)
    }
}