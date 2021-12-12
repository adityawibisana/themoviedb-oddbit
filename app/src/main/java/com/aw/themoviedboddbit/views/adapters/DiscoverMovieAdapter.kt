package com.aw.themoviedboddbit.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aw.themoviedboddbit.R
import com.aw.themoviedboddbit.api.API
import com.aw.themoviedboddbit.databinding.DiscoveryMovieItemBinding
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.models.entity.Movie
import com.bumptech.glide.Glide


class DiscoverMovieAdapter(private val movies: List<Movie>, private val genreDao: GenreDao) :
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
        holder.binding.overview.text = movie.overview
        holder.binding.popularity.text = movie.popularity.toString()

        if (movie.genre_ids.isNotEmpty()) {
            genreDao.get(movie.genre_ids[0]).observe(holder.itemView.context as LifecycleOwner, {
                holder.binding.genre.text = it
            })
        }

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