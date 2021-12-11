package com.aw.themoviedboddbit.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aw.themoviedboddbit.databinding.DiscoveryMovieItemBinding
import com.aw.themoviedboddbit.models.entity.Movie

class DiscoverMovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<DiscoverMovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: DiscoveryMovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DiscoveryMovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.title.text =  movie.title
    }

    override fun getItemCount() = movies.size



}