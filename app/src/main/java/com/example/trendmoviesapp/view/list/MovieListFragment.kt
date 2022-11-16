package com.example.trendmoviesapp.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trendmoviesapp.R
import com.example.trendmoviesapp.databinding.FragmentMovieListBinding
import com.example.trendmoviesapp.remote.model.Movie
import com.example.trendmoviesapp.utils.Constant
import com.example.trendmoviesapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.loading_message_layout.*

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var binding: FragmentMovieListBinding? = null

    private val movieListViewModel by viewModels<MovieListViewModel>()
    lateinit var adapter: MovieAdapter
    private var moviesList = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constant.listKey, moviesList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(requireContext()) { movie ->
            val bundle = Bundle()
            bundle.putInt(Constant.id, movie.id)
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment, bundle
            )
        }

        if (savedInstanceState != null) {
            showHideLoading()
            val list =
                savedInstanceState.getParcelableArrayList<Movie>(Constant.listKey)
            moviesList = list as ArrayList<Movie>
            adapter.addAllMovies(list as ArrayList<Movie>)
            rv_movies.adapter = adapter

        } else {
            initObserverData()
        }
    }

    private fun initObserverData() {

        movieListViewModel.refreshMovieList().observe(viewLifecycleOwner) { response ->
            when (response.getStatus()) {

                DataState.DataStatus.LOADING -> {
                    showHideLoading(isLoading = true)
                }
                DataState.DataStatus.SUCCESS -> {
                    showHideLoading()
                    val data = response.getData()?.results
                    moviesList = data as ArrayList<Movie>
                    adapter.addAllMovies(data as ArrayList<Movie>)
                    rv_movies.adapter = adapter
                }
                DataState.DataStatus.ERROR -> {
                    showHideLoading(hasError = true, txt = response.getError()?.message.toString())
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
                initObserverData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}