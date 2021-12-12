package com.aw.themoviedboddbit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aw.themoviedboddbit.databinding.ActivityMainBinding
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.models.entity.Movie
import com.aw.themoviedboddbit.viewModels.MainActivityViewModel
import com.aw.themoviedboddbit.views.adapters.DiscoverMovieAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var genreDao: GenreDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.movieList.observe(this, Observer {
            it ?: return@Observer
            val adapter = DiscoverMovieAdapter(it, genreDao)
            binding.movieList.adapter = adapter
            binding.movieList.layoutManager = LinearLayoutManager(this)
        })

        binding.menuSortVoteCount.setOnClickListener {
            viewModel.sortByVoteCount()
            binding.fab.close(true)
        }
        binding.menuSortPopularity.setOnClickListener {
            viewModel.sortByPopularity()
            binding.fab.close(true)
        }
        binding.menuSortDate.setOnClickListener {
            viewModel.sortByReleaseDate()
            binding.fab.close(true)
        }
        binding.filterDate.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Toast.makeText(this, "Sorry, this feature is currently only for Android Oreo and above", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText(getString(R.string.pick_date))
                    .build().also {
                    it.addOnPositiveButtonClickListener { pair ->
                        viewModel.filterDate(pair.first, pair.second)
                    }
                    it.show(supportFragmentManager, null)
                }

            binding.fab.close(true)
        }
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMovieItemClicked(movie: Movie) {
        Intent(this, MovieDetailActivity::class.java).also {
            it.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
            it.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.title)
            it.putExtra(MovieDetailActivity.EXTRA_POSTER, movie.poster_path)
            it.putExtra(MovieDetailActivity.EXTRA_DESCRIPTION, movie.overview)
            it.putExtra(MovieDetailActivity.EXTRA_POPULARITY, movie.popularity)
            it.putExtra(MovieDetailActivity.EXTRA_RELEASE_DATE, movie.release_date)
            it.putExtra(MovieDetailActivity.EXTRA_BACKDROP, movie.backdrop_path)
            it.putExtra(MovieDetailActivity.EXTRA_LANGUAGE, movie.original_language)
            it.putExtra(MovieDetailActivity.EXTRA_VOTE_AVERAGE, movie.vote_average)
            it.putExtra(MovieDetailActivity.EXTRA_VOTE_COUNT, movie.vote_count)
            startActivity(it)
        }
    }
}