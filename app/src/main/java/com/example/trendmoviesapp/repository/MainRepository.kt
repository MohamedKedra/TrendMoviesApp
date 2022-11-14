package com.example.trendmoviesapp.repository

import com.example.trendmoviesapp.remote.MovieService
import com.example.trendmoviesapp.utils.Constant
import javax.inject.Inject

class MainRepository @Inject constructor(private val service: MovieService) {

    suspend fun getTrendList() = service.getTrendMovieList(Constant.apiKey)
}