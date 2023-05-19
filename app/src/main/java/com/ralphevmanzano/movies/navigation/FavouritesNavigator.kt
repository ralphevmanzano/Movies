package com.ralphevmanzano.movies.navigation

import androidx.navigation.NavController
import com.ralphevmanzano.movies.NavGraphDirections
import com.ralphevmanzano.movies.favourites.navigation.FavouritesNavigation
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class FavouritesNavigator @Inject constructor(
    private val navController: NavController
): FavouritesNavigation {

    override fun navigateToDetails(movieId: Int) {
        navController.navigate(NavGraphDirections.actionToDetails(movieId))
    }
}