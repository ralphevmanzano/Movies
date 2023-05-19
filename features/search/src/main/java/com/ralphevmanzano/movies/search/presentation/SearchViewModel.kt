package com.ralphevmanzano.movies.search.presentation

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    private val _queryFlow = MutableStateFlow("")

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            val genres = repository.loadGenres()
            _genres.postValue(genres)

        }
    }

    fun doSearch(query: String): Flow<PagingData<Pair<Movie, Int>>> {
        _queryFlow.value = query
        val pagingData = _queryFlow.flatMapLatest { query ->
            repository.searchMovies(query).cachedIn(viewModelScope)
        }
        return pagingData
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}