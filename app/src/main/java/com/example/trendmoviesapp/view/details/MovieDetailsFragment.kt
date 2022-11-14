package com.example.trendmoviesapp.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.trendmoviesapp.R
import com.example.trendmoviesapp.databinding.FragmentMovieDetailsBinding
import com.example.trendmoviesapp.remote.model.MovieResponse
import com.example.trendmoviesapp.utils.Constant
import com.example.trendmoviesapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.loading_message_layout.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding? = null
    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
    }


    private fun initObservable() {
        val id = arguments?.getInt(Constant.id).toString().toInt()
        movieDetailsViewModel.getMovieDetails(id).observe(viewLifecycleOwner) {
            when (it.getStatus()) {
                DataState.DataStatus.LOADING -> {
                    showHideLoading(isLoading = true)
                }
                DataState.DataStatus.SUCCESS -> {
                    showHideLoading()
                    mapResponseToUi(it.getData())
                }
                DataState.DataStatus.ERROR -> {
                    showHideLoading(hasError = true, txt = it.getError()?.message.toString())
                }
                DataState.DataStatus.NO_INTERNET -> {
                    showHideLoading(
                        hasError = true,
                        txt = getString(R.string.no_internet_connection)
                    )
                }
            }
        }

    }

    private fun mapResponseToUi(movieResponse: MovieResponse?) {

        with(binding){
            movieResponse?.let { movie ->
                tv_title.text = movie.title
                tv_overview.text = movie.overview
                tv_date.text = movie.release_date
                tv_rate.text = movie.vote_average.toString()
                tv_lang.text = movie.original_language
                tv_popularity.text = movie.popularity.toInt().toString()

                Glide.with(requireActivity()).load(Constant.imageBase.plus(movie.poster_path)).into(iv_poster)
                Glide.with(requireActivity()).load(Constant.imageBase.plus(movie.backdrop_path)).into(iv_bg)
            }
        }
    }

    private fun showHideLoading(
        isLoading: Boolean = false,
        hasError: Boolean = false,
        txt: String = ""
    ) {
        with(binding) {
            pb_progressbar.isVisible = isLoading
            tv_error.isVisible = hasError
            tv_error.text = txt
            btn_retry.isVisible = hasError
            btn_retry.setOnClickListener {
                initObservable()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}