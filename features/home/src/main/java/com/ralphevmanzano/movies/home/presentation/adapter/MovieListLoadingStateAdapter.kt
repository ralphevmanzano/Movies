package com.ralphevmanzano.movies.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ralphevmanzano.movies.home.databinding.ItemLoadingStateBinding
import com.ralphevmanzano.movies.home.presentation.holder.MovieListLoadingStateHolder

class MovieListLoadingStateAdapter(private val adapter: MovieListAdapter) :
    LoadStateAdapter<MovieListLoadingStateHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieListLoadingStateHolder {
        val binding =
            ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListLoadingStateHolder(binding) {
            adapter.retry()
        }
    }

    override fun onBindViewHolder(holder: MovieListLoadingStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}