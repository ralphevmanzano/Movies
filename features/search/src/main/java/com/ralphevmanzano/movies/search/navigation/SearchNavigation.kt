package com.ralphevmanzano.movies.search.navigation

interface SearchNavigation {
    fun navigateToDetails(movieId: Int)
    fun navigateBack()
}