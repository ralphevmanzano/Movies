package com.ralphevmanzano.movies.shared.utils.holder

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.scale
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.shared.R
import com.ralphevmanzano.movies.shared.databinding.ItemMovieListBinding

class MovieListHolder private constructor(
    private val binding: ItemMovieListBinding,
    private val glide: RequestManager,
    private val genres: List<Genre>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Pair<Movie, Int>) = with(binding) {
        val movie = data.first
        val context = binding.root.context
        val genreText = genres.filter { it.id in movie.genreIds }.take(3).joinToString { it.name }
        val spannableTitle = SpannableStringBuilder()
            .bold { append(movie.title) }
            .append(" ")
            .scale(0.9f) { append(context.getString(R.string.release_year, movie.releaseYear)) }

        titleTextView.text = spannableTitle
        genreTextView.text = genreText
        glide.load(movie.posterUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(posterImageView)
        ratingTextView.text = movie.voteAverage.toString()
        votesTextView.text = context.getString(R.string.votes, movie.voteCount)

        itemView.setOnClickListener { onItemClick(movie) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            genres: List<Genre>,
            onItemClick: (Movie) -> Unit
        ): MovieListHolder {
            val binding =
                ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieListHolder(binding, glide, genres, onItemClick)
        }
    }
}