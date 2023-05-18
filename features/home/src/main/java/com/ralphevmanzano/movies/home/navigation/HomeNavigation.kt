package com.ralphevmanzano.movies.home.navigation

interface HomeNavigation {
    // class for navigating between different modules

    fun navigateToDetails(movieId: Int)
    fun navigateToSearch()
}