package com.aw.themoviedboddbit

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aw.themoviedboddbit.api.API
import com.aw.themoviedboddbit.databinding.ActivityMovieDetailBinding
import com.aw.themoviedboddbit.viewModels.MovieDetailActivityViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    var movieId: Int = -1
    var movieTitle: String? = ""
    var moviePoster: String? = ""
    var movieDescription: String? = ""
    var moviePopularity: Float? = .0f
    var movieReleaseDate: String? = ""
    var movieBackDrop: String? = ""
    var movieLanguage: String? = ""
    var movieVoteAverage: Float? = .0f
    var movieVoteCount: Int? = 0

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, movieId)
        movieTitle = intent.getStringExtra(EXTRA_TITLE)
        moviePoster = intent.getStringExtra(EXTRA_POSTER)
        movieDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        moviePopularity = intent.getFloatExtra(EXTRA_POPULARITY, .0f)
        movieReleaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE)
        movieBackDrop = intent.getStringExtra(EXTRA_BACKDROP)
        movieLanguage = intent.getStringExtra(EXTRA_LANGUAGE)
        movieVoteAverage = intent.getFloatExtra(EXTRA_VOTE_AVERAGE, .0f)
        movieVoteCount = intent.getIntExtra(EXTRA_VOTE_COUNT, 0)

        Glide
            .with(this)
            .load("${API.BASE_BACKDROP_PATH}${movieBackDrop}")
            .placeholder(R.drawable.ic_baseline_data_usage_24)
            .into(binding.backdrop)

        binding.title.text = movieTitle
        binding.overview.text = movieDescription
        binding.releaseDate.text = resources.getString(R.string.release_date, movieReleaseDate)
        binding.language.text = resources.getString(R.string.language, movieLanguage)

        moviePopularity?.also {
            binding.popularity.text =  resources.getString(R.string.vote_detail_value, movieVoteCount.toString(), it.toString())
        }

        binding.voteAverage.text = movieVoteAverage.toString()

        viewModel = ViewModelProvider(this).get(MovieDetailActivityViewModel::class.java)
        viewModel.initialize(movieId)

        viewModel.genre.observe(this, Observer {
            binding.genres.text = it
        })

        viewModel.productionCompanies.observe(this, {
            binding.productionCompanies.text = it
        })

        viewModel.revenue.observe(this, {
            val formattedRevenue = NumberFormat.getNumberInstance(Locale.US).format(it)

            binding.revenue.text = resources.getString(R.string.revenue_value, if (formattedRevenue == "0") "-" else formattedRevenue)
        })

        viewModel.duration.observe(this, {
            binding.duration.text = resources.getString(R.string.duration_value, it)
        })

        viewModel.homepage.observe(this, {
            binding.homepage.text = resources.getString(R.string.homepage_value, it)
        })

        supportActionBar?.hide()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    companion object {
        const val EXTRA_MOVIE_ID = "id"
        const val EXTRA_TITLE = "title"
        const val EXTRA_POSTER = "poster"
        const val EXTRA_DESCRIPTION = "description" // overview
        const val EXTRA_POPULARITY = "popularity"
        const val EXTRA_RELEASE_DATE = "release_date"
        const val EXTRA_BACKDROP = "backdrop"
        const val EXTRA_LANGUAGE = "language"
        const val EXTRA_VOTE_AVERAGE = "vote_average"
        const val EXTRA_VOTE_COUNT = "vote_count"
    }
}