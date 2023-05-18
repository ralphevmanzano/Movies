package com.ralphevmanzano.movies.home.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.home.presentation.holder.MovieListHolder

class MovieListAdapter(
    private val glide: RequestManager,
    private val genres: List<Genre>,
    private val onItemClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieListHolder>(MovieDiffCallBack()) {

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder.create(parent, glide, genres, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        val data = getItem(holder.bindingAdapterPosition)
        data?.let { holder.bind(it) }
    }
}