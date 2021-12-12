package com.aw.themoviedboddbit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aw.themoviedboddbit.databinding.ActivityMainBinding
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.viewModels.MainActivityViewModel
import com.aw.themoviedboddbit.views.adapters.DiscoverMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
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
        }
        binding.menuSortPopularity.setOnClickListener {
            viewModel.sortByPopularity()
        }
    }
}