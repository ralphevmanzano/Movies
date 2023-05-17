package com.ralphevmanzano.movies.home.presentation.holder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ralphevmanzano.movies.home.databinding.ItemLoadingStateBinding

class MovieListLoadingStateHolder(
    private val binding: ItemLoadingStateBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) = with(binding) {
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
        errorTextView.isVisible = !(loadState as LoadState.Error).error.message.isNullOrBlank()
        errorTextView.text = loadState.error.message.orEmpty()
    }
}