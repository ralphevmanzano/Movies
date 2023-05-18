package com.ralphevmanzano.movie.details.presentation

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.scale
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.ralphevmanzano.movies.details.R.*
import com.ralphevmanzano.movies.details.R.string.rating_total
import com.ralphevmanzano.movies.details.databinding.FragmentDetailsBinding
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.shared.R
import com.ralphevmanzano.movies.shared.utils.getDrawable
import com.ralphevmanzano.movies.shared.utils.hide
import com.ralphevmanzano.movies.shared.utils.invisible
import com.ralphevmanzano.movies.shared.utils.observeResult
import com.ralphevmanzano.movies.shared.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel>()

    private lateinit var binding: FragmentDetailsBinding

    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments?.containsKey(MOVIE_ID) == true) {
            movieId = arguments?.getInt(MOVIE_ID)
            movieId?.let { viewModel.getMovieDetails(it) }
        }
        setupUI()
        setupObservers()
    }

    private fun setupUI() = with(binding) {
        backButton.setOnClickListener { findNavController().popBackStack() }
        favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun updateUI(movie: Movie) = with(binding) {
        viewModel.setMovie(movie)

        val glide = Glide.with(this@DetailsFragment)
        glide.load(movie.backdropUrl).into(backDropImageView)
        glide.load(movie.posterUrl).into(posterImageView)

        val spannableTitle = SpannableStringBuilder()
            .bold { append(movie.title) }
            .append(" ")
            .scale(0.9f) {
                append(
                    getString(
                        R.string.release_year,
                        movie.releaseYear
                    )
                )
            }

        val voteRating = "%.1f".format(movie.voteAverage)
        val spannableRating = SpannableStringBuilder()
            .bold { scale(1.25f) { append(voteRating) } }
            .append(getString(rating_total))

        titleTextView.text = spannableTitle
        ratingTextView.text = spannableRating
        votesTextView.text = getString(R.string.votes_with_label, movie.voteCount)
        overViewTextView.text = movie.overview

        if (movie.tagLine.isNotBlank()) {
            tagLineTextView.text = movie.tagLine
        } else tagLineTextView.hide()

        runtimeTextView.text = getRuntimeInHrs(movie.runtime)

        val languages = movie.spokenLanguages.joinToString { it.englishName }
        languageTextView.text = languages
        statusTextView.text = movie.status

        setupChips(movie)
    }

    private fun getRuntimeInHrs(runtimeMin: Int): String {
        val hrs = runtimeMin / 60
        val min = runtimeMin % 60
        return "${hrs}h ${min}min"
    }

    private fun setupChips(movie: Movie) {
        movie.genres.map { it.name }.forEach { genre ->
            val chip =
                layoutInflater.inflate(layout.item_chip_genre, binding.chipGroup, false) as Chip
            chip.text = genre
            binding.chipGroup.addView(chip)
        }
    }

    private fun setupObservers() = with(binding) {
        viewModel.favorites.observe(viewLifecycleOwner) { faves ->
            viewModel.setIsFavorite(faves.map { it.id }.contains(movieId))
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_fave_button_filled))
            } else {
                favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_fave_button))
            }
        }

        observeResult(
            viewModel.detailsResult,
            onLoading = {
                progressBar.show()
                contentGroup.invisible()
            }, onSuccess = {
                progressBar.hide()
                contentGroup.show()
                updateUI(it)
            }, onError = {
                progressBar.hide()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    companion object {
        const val MOVIE_ID = "movieId"
    }
}