package com.ralphevmanzano.movies.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.home.databinding.FragmentHomeBinding
import com.ralphevmanzano.movies.home.presentation.adapter.HomeStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() = with(binding) {
        initViewPager()

        nowPlayingChip.setOnClickListener {
            viewPager.setCurrentItem(0, true)
        }

        popularChip.setOnClickListener {
            viewPager.setCurrentItem(1, true)
        }

        topRatedChip.setOnClickListener {
            viewPager.setCurrentItem(2, true)
        }

        upcomingChip.setOnClickListener {
            viewPager.setCurrentItem(3, true)
        }
    }

    private fun initViewPager() = with(binding) {
        val adapter = HomeStateAdapter(this@HomeFragment.childFragmentManager, lifecycle)
        adapter.addFragment(MovieListFragment.create(Movie.Type.NOW_PLAYING))
        adapter.addFragment(MovieListFragment.create(Movie.Type.POPULAR))
        adapter.addFragment(MovieListFragment.create(Movie.Type.TOP_RATED))
        adapter.addFragment(MovieListFragment.create(Movie.Type.UPCOMING))
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val chipList = listOf(nowPlayingChip, popularChip, topRatedChip, upcomingChip)
                chipList[position].isSelected = true
            }
        })
    }
}