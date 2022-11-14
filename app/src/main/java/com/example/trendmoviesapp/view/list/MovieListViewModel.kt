package com.example.trendmoviesapp.view.list

import androidx.lifecycle.viewModelScope
import com.example.trendmoviesapp.remote.model.TrendingMovieResponse
import com.example.trendmoviesapp.repository.MainRepository
import com.example.trendmoviesapp.utils.BaseViewModel
import com.example.trendmoviesapp.utils.ConnectionManager
import com.example.trendmoviesapp.utils.LiveDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val trendMoviesListResponse = LiveDataState<TrendingMovieResponse>()

    fun refreshMovieList(): LiveDataState<TrendingMovieResponse> {

        publishLoading(trendMoviesListResponse)

        if (!connectionManager.isNetworkAvailable){
            publishNoInternet(trendMoviesListResponse)
            return trendMoviesListResponse
        }

        viewModelScope.launch {

            val result = repository.getTrendList()
            if (result.isSuccessful){
                publishResult(trendMoviesListResponse,result.body())
            }else{
                publishError(trendMoviesListResponse,result.message())
            }
        }

        return trendMoviesListResponse
    }
}