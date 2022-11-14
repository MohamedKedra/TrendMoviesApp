package com.example.trendmoviesapp.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.trendmoviesapp.databinding.ItemMovieBinding
import com.example.trendmoviesapp.remote.model.Movie
import com.example.trendmoviesapp.utils.Constant

class MovieAdapter(private val context: Context) : Adapter<MovieAdapter.MovieHolder>() {

    private var movies = ArrayList<Movie>()

    inner class MovieHolder(private val movieBinding: ItemMovieBinding) :
        ViewHolder(movieBinding.root) {
        fun bind(movie: Movie) {
            movieBinding.tvTitle.text = movie.title
            Glide.with(context).load(Constant.imageBase.plus(movie.poster_path))
                .into(movieBinding.ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) =
        holder.bind(movies[position])

    fun addAllMovies(movies: ArrayList<Movie>) {
        this.movies = movies
    }

    override fun getItemCount(): Int = movies.size
}