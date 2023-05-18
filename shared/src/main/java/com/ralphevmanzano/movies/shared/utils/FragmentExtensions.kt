package com.ralphevmanzano.movies.shared.utils

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.ralphevmanzano.movies.domain.model.utils.Result

fun <T> Fragment.observeResult(
    liveData: LiveData<Result<T>>,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit,
    onLoading: () -> Unit
) {
    liveData.observe(viewLifecycleOwner) { result ->
        when (result.status) {
            Result.Status.LOADING -> onLoading()
            Result.Status.SUCCESS -> result.data?.let { onSuccess(it) }
            Result.Status.ERROR -> onError(result.message.orEmpty())
        }
    }
}

fun Fragment.getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), id)
}