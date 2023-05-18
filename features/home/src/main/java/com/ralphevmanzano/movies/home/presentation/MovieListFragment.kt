package com.ralphevmanzano.movies.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.home.databinding.FragmentMovieListBinding
import com.ralphevmanzano.movies.home.navigation.HomeNavigation
import com.ralphevmanzano.movies.home.presentation.adapter.MovieListAdapter
import com.ralphevmanzano.movies.home.presentation.adapter.MovieListLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieListViewModel>()

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieListAdapter
    private lateinit var type: Movie.Type

    @Inject
    lateinit var homeNavigation: HomeNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        type = arguments?.getSerializable(TYPE) as Movie.Type
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObservers()
    }

    private fun setupUI() = with(binding) {
    }

    private fun setupObservers() {
        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            if (genres.isNotEmpty()) {
                setupRecyclerView(genres)
            }
        }
    }

    private fun setupRecyclerView(genres: List<Genre>) {
        val glide = Glide.with(this)
        adapter = MovieListAdapter(glide, genres) {
            homeNavigation.navigateToDetails(it.id)
        }
        adapter.withLoadStateHeaderAndFooter(
            header = MovieListLoadingStateAdapter(adapter),
            footer = MovieListLoadingStateAdapter(adapter)
        )
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        loadMovieList()
    }

    private fun loadMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (type) {
                Movie.Type.NOW_PLAYING -> {
                    viewModel.getNowPlaying().collectLatest {
                        adapter.submitData(it)
                    }
                }

                Movie.Type.POPULAR -> {
                    viewModel.getPopular().collectLatest {
                        adapter.submitData(it)
                    }
                }

                Movie.Type.TOP_RATED -> {
                    viewModel.getTopRated().collectLatest {
                        adapter.submitData(it)
                    }
                }

                Movie.Type.UPCOMING -> {
                    viewModel.getUpcoming().collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val TYPE = "TYPE"

        fun create(type: Movie.Type): MovieListFragment {
            val bundle = Bundle().also {
                it.putSerializable(TYPE, type)
            }
            return MovieListFragment().apply {
                arguments = bundle
            }
        }
    }
}
