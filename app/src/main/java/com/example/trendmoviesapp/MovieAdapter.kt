package com.example.trendmoviesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.trendmoviesapp.databinding.ItemMovieBinding

class MovieAdapter : Adapter<MovieAdapter.MovieHolder>(){

    inner class MovieHolder(movieBinding: ItemMovieBinding) : ViewHolder(movieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    }

    override fun getItemCount(): Int = 15
}