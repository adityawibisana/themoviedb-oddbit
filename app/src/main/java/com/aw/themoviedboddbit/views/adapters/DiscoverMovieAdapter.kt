package com.aw.themoviedboddbit.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aw.themoviedboddbit.R
import com.aw.themoviedboddbit.api.API
import com.aw.themoviedboddbit.databinding.DiscoveryMovieItemBinding
import com.aw.themoviedboddbit.models.entity.Movie
import com.bumptech.glide.Glide


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
        movie.poster_path?.also {
            Glide
            .with(holder.itemView.context)
            .load("${API.BASE_POSTER_PATH}${it}")
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_data_usage_24)
            .into(holder.binding.poster)
        }

    }

    override fun getItemCount() = movies.size



}