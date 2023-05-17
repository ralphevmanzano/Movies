package com.ralphevmanzano.movies.home.presentation.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.home.databinding.ItemMovieListBinding

class MovieListHolder private constructor(
    private val binding: ItemMovieListBinding,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) = with(binding) {
        val context = binding.root.context
        titleTextView.text = movie.title
        dateTextView.text = movie.releaseDate
        // TODO: handle genre, create splash call API (if DB empty) store in DB
        // TODO: get genre and put in variable, access variable when mapping movies
        Glide.with(context).load(movie.posterUrl).into(posterImageView)
        val progress = movie.voteAverage * 10
        ratingProgress.progress = progress.toInt()
        ratingTextView.text = progress.toString()
        votesTextView.text = "(${movie.voteCount} votes)"

        itemView.setOnClickListener { onItemClick(movie) }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (Movie) -> Unit): MovieListHolder {
            val binding =
                ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieListHolder(binding, onItemClick)
        }
    }
}