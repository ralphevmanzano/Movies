package com.ralphevmanzano.movies.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ralphevmanzano.movies.R
import com.ralphevmanzano.movies.home.navigation.HomeNavigation
import com.ralphevmanzano.movies.navigation.HomeNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NavControllerModule {

    @Provides
    fun navController(activity: FragmentActivity): NavController {
        return (activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)!! as NavHostFragment).navController
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun homeNavigation(navigator: HomeNavigator): HomeNavigation
    // add other navigators here
}