package com.ralphevmanzano.movies.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.home.databinding.FragmentMovieListBinding
import com.ralphevmanzano.movies.home.presentation.adapter.MovieListAdapter
import com.ralphevmanzano.movies.home.presentation.adapter.MovieListLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieListViewModel>()

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieListAdapter
    private lateinit var type: Movie.Type

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
        initUI()
        collectUIState()
    }

    private fun initUI() = with(binding) {
        adapter = MovieListAdapter {}
        adapter.withLoadStateHeaderAndFooter(
            header = MovieListLoadingStateAdapter(adapter),
            footer = MovieListLoadingStateAdapter(adapter)
        )
        binding.recyclerView.adapter = adapter

    }

    private fun collectUIState() {
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
