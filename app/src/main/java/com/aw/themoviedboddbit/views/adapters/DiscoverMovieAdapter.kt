package com.aw.themoviedboddbit.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aw.themoviedboddbit.R
import com.aw.themoviedboddbit.api.API
import com.aw.themoviedboddbit.databinding.DiscoveryMovieItemBinding
import com.aw.themoviedboddbit.db.FavoriteDao
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.event.FavoriteClickedEvent
import com.aw.themoviedboddbit.models.entity.Movie
import com.bumptech.glide.Glide
import org.greenrobot.eventbus.EventBus


class DiscoverMovieAdapter(private val movies: List<Movie>, private val genreDao: GenreDao, private val favoriteDao: FavoriteDao) :
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

        favoriteDao.getById(movie.id)?.observe(holder.itemView.context as LifecycleOwner, {
            if (it == null) {
                holder.binding.favorite.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_baseline_favorite_border_24, null)
            } else {
                holder.binding.favorite.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_baseline_favorited_24, null)
            }
        })

        holder.binding.favorite.setOnClickListener {
            EventBus.getDefault().post(FavoriteClickedEvent(movie))
        }


        movie.poster_path?.also {
            Glide
            .with(holder.itemView.context)
            .load("${API.BASE_POSTER_PATH}${it}")
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_data_usage_24)
            .into(holder.binding.poster)
        }

        holder.binding.root.setOnClickListener {
            EventBus.getDefault().post(movie)
        }
    }

    override fun getItemCount() = movies.size



}