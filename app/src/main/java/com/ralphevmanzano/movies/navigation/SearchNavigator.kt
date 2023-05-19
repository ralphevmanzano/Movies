package com.ralphevmanzano.movies.navigation

import androidx.navigation.NavController
import com.ralphevmanzano.movies.NavGraphDirections
import com.ralphevmanzano.movies.search.navigation.SearchNavigation
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SearchNavigator @Inject constructor(
    private val navController: NavController
): SearchNavigation {

    override fun navigateToDetails(movieId: Int) {
        navController.navigate(NavGraphDirections.actionToDetails(movieId))
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}