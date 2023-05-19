package com.ralphevmanzano.movies.presentation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ralphevmanzano.movies.NavGraphDirections
import com.ralphevmanzano.movies.R
import com.ralphevmanzano.movies.databinding.ActivityMainBinding
import com.ralphevmanzano.movies.details.R.id.fragment_details
import com.ralphevmanzano.movies.home.R.id.fragment_home
import com.ralphevmanzano.movies.search.R.id.fragment_search
import com.ralphevmanzano.movies.shared.utils.hide
import com.ralphevmanzano.movies.shared.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var toolbarMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val navView: BottomNavigationView = binding.content.bottomNavView
        navView.setupWithNavController(navController)

        val menuSearch = binding.toolbar.menu.findItem(R.id.menu_search)

        menuSearch.setOnMenuItemClickListener {
            navController.navigate(NavGraphDirections.actionToSearch())
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                fragment_details, fragment_search -> {
                    binding.content.bottomNavView.hide()
                    binding.toolbar.hide()
                }
                else -> {
                    if (destination.id == fragment_home) {
                        binding.toolbar.title = getString(R.string.movies)
                        menuSearch.isVisible = true
                    } else {
                        binding.toolbar.title = getString(R.string.favourites)
                        menuSearch.isVisible = false
                    }
                    binding.content.bottomNavView.show()
                    binding.toolbar.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}