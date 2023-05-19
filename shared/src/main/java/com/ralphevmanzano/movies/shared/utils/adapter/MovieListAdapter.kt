package com.ralphevmanzano.movies.shared.utils.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.ralphevmanzano.movies.domain.model.Genre
import com.ralphevmanzano.movies.domain.model.Movie
import com.ralphevmanzano.movies.shared.utils.holder.MovieListHolder

class MovieListAdapter(
    private val glide: RequestManager,
    private val genres: List<Genre>,
    private val onBindItem: ((Int) -> Unit)? = null,
    private val onItemClick: (Movie) -> Unit
) : PagingDataAdapter<Pair<Movie, Int>, MovieListHolder>(MovieDiffCallBack()) {

    class MovieDiffCallBack : DiffUtil.ItemCallback<Pair<Movie, Int>>() {
        override fun areItemsTheSame(oldItem: Pair<Movie, Int>, newItem: Pair<Movie, Int>) = oldItem.first.id == newItem.first.id
        override fun areContentsTheSame(oldItem: Pair<Movie, Int>, newItem: Pair<Movie, Int>) = oldItem.first == newItem.first
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder.create(parent, glide, genres, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        val data = getItem(holder.bindingAdapterPosition)
        data?.let {
            onBindItem?.invoke(it.first.totalResults)
            holder.bind(it)
        }
    }
}