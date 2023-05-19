package com.ralphevmanzano.movie.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.domain.model.utils.Result
import com.ralphevmanzano.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: MoviesRepository): ViewModel() {

    private val _detailsResult = MutableLiveData<Result<Movie>>()
    val detailsResult: LiveData<Result<Movie>> = _detailsResult

    private val _favorites = MutableLiveData<List<Movie>>()
    val favorites: LiveData<List<Movie>> = _favorites

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var movie: Movie? = null

    init {
        viewModelScope.launch {
            repository.loadFavourites().collect {
                _favorites.postValue(it)
            }
        }
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.getDetails(id).collect {
                _detailsResult.postValue(it)
            }
        }
    }

    fun setIsFavorite(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    fun setMovie(movie: Movie) {
        this.movie = movie
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value!!

        viewModelScope.launch {
            if (_isFavorite.value == true) {
                movie?.let { repository.addFavourite(it) }
            } else {
                movie?.let { repository.removeFavourite(it.id) }
            }
        }
    }
}