package com.ralphevmanzano.movies.navigation

import androidx.navigation.NavController
import com.ralphevmanzano.movies.NavGraphDirections
import com.ralphevmanzano.movies.home.navigation.HomeNavigation
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HomeNavigator @Inject constructor(
    private val navController: NavController
): HomeNavigation {

    override fun navigateToDetails(movieId: Int) {
        navController.navigate(NavGraphDirections.actionToDetails(movieId))
    }

    override fun navigateToSearch() {
        TODO("Not yet implemented")
    }
}