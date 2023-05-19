package com.ralphevmanzano.movies.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    private val _favourites = MutableLiveData<List<Movie>>()
    val favourites: LiveData<List<Movie>> = _favourites

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres


    init {
        viewModelScope.launch {
            val genres = repository.loadGenres()
            _genres.postValue(genres)

        }
    }

    fun loadFavourites() {
        viewModelScope.launch {
            repository.loadFavourites().collect {
                _favourites.postValue(it)
            }
        }
    }

}