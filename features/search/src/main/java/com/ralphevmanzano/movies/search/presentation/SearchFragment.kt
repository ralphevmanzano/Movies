package com.ralphevmanzano.movies.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.search.R
import com.ralphevmanzano.movies.search.databinding.FragmentSearchBinding
import com.ralphevmanzano.movies.search.navigation.SearchNavigation
import com.ralphevmanzano.movies.shared.utils.adapter.MovieListAdapter
import com.ralphevmanzano.movies.shared.utils.hide
import com.ralphevmanzano.movies.shared.utils.show
import com.ralphevmanzano.movies.shared.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: MovieListAdapter

    @Inject
    lateinit var searchNavigation: SearchNavigation

    private var debounceJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObservers()
    }

    private fun setupUI() = with(binding) {
        searchEditText.showKeyboard()
        searchEditText.doAfterTextChanged { query ->
            if (query.isNullOrBlank()) {
                progressBar.hide()
                recyclerView.hide()
                resultsTextView.hide()
                noResultsTextView.hide()
                startSearchingTextView.show()

                debounceJob?.cancel()
            } else {
                debounceJob?.cancel()
                debounceJob = viewModel.viewModelScope.launch {
                    delay(300)
                    viewModel.doSearch(query.toString()).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }

        backButton.setOnClickListener {
            searchNavigation.navigateBack()
        }
    }

    private fun setupObservers() {
        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            if (genres.isNotEmpty()) {
                setupRecyclerView(genres)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else binding.progressBar.hide()
        }
    }

    private fun setupRecyclerView(genres: List<Genre>) = with(binding) {
        val glide = Glide.with(this@SearchFragment)
        adapter = MovieListAdapter(
            glide, genres,
            onBindItem = {
                resultsTextView.text = getString(R.string.found_movies, it)
            }
        ) { searchNavigation.navigateToDetails(it.id) }

        adapter.addLoadStateListener { loadState ->
            viewModel.setLoading(loadState.source.refresh is LoadState.Loading)
            if (loadState.source.refresh is LoadState.NotLoading) {
                if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    resultsTextView.hide()
                    startSearchingTextView.hide()
                    noResultsTextView.show()
                } else {
                    resultsTextView.show()
                    startSearchingTextView.hide()
                    noResultsTextView.hide()
                    recyclerView.show()
                }
            }

            if (loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    requireContext(),
                    (loadState.refresh as LoadState.Error).error.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }
}