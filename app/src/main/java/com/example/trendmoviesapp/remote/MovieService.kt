package com.example.trendmoviesapp.remote

import com.example.trendmoviesapp.remote.model.TrendingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getTrendMovieList(@Query("api_key") key: String) : Response<TrendingMovieResponse>
}