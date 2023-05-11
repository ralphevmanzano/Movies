package com.ralphevmanzano.movies.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.Response

class MovieApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val apiKey = "0e7274f05c36db12cbe71d9ab0393d47"
        val url = original.url.newBuilder().addQueryParameter("api_key", apiKey).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}