package com.ralphevmanzano.movies.domain.model.utils

data class Result<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val code: String? = null
) {
    companion object {

        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(msg: String, code: String? = null, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, msg, code)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING)
        }
    }
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}