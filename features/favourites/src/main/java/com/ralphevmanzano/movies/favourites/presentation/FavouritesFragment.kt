package com.ralphevmanzano.movies.favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.favourites.databinding.FragmentFavouritesBinding
import com.ralphevmanzano.movies.favourites.navigation.FavouritesNavigation
import com.ralphevmanzano.movies.favourites.presentation.adapter.FavouritesAdapter
import com.ralphevmanzano.movies.shared.utils.hide
import com.ralphevmanzano.movies.shared.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment: Fragment() {

    private val viewModel by viewModels<FavouritesViewModel>()

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavouritesAdapter

    @Inject
    lateinit var favouritesNavigation: FavouritesNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            if (genres.isNotEmpty()) {
                setupRecyclerView(genres)
            }
        }

        viewModel.favourites.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recyclerView.hide()
                binding.emptyNoteTextView.show()
            } else {
                binding.recyclerView.show()
                binding.emptyNoteTextView.hide()
                adapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView(genres: List<Genre>) {
        val glide = Glide.with(this)
        adapter = FavouritesAdapter(glide, genres) {
            favouritesNavigation.navigateToDetails(it.id)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewModel.loadFavourites()
    }
}