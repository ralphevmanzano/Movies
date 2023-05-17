package com.ralphevmanzano.movies.home.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeStateAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val list = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
    }

    override fun getItemCount() = list.size


    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}