package com.example.trendmoviesapp.view.details

import androidx.lifecycle.viewModelScope
import com.example.trendmoviesapp.remote.model.MovieResponse
import com.example.trendmoviesapp.repository.MainRepository
import com.example.trendmoviesapp.utils.BaseViewModel
import com.example.trendmoviesapp.utils.ConnectionManager
import com.example.trendmoviesapp.utils.LiveDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val movieDetailsResponse = LiveDataState<MovieResponse>()

    fun getMovieDetails(movieId: Int): LiveDataState<MovieResponse> {

        publishLoading(movieDetailsResponse)

        if (!connectionManager.isNetworkAvailable) {
            publishNoInternet(movieDetailsResponse)
            return movieDetailsResponse
        }

        viewModelScope.launch {

            val result = repository.getMovieDetails(movieId.toString())
            if (result.isSuccessful) {
                publishResult(movieDetailsResponse, result.body())
            } else {
                publishError(movieDetailsResponse, result.message())
            }
        }

        return movieDetailsResponse
    }
}