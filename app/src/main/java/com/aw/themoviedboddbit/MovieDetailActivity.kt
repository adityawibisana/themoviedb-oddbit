package com.aw.themoviedboddbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aw.themoviedboddbit.api.API
import com.aw.themoviedboddbit.databinding.ActivityMainBinding
import com.aw.themoviedboddbit.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {

    var movieId: Int = -1
    var movieTitle: String? = ""
    var moviePoster: String? = ""
    var movieDescription: String? = ""
    var moviePopularity: Double? = .0
    var movieReleaseDate: String? = ""
    var movieBackDrop: String? = ""
    var movieLanguage: String? = ""
    var movieVoteAverage: Float? = .0f
    var movieVoteCount: Int? = 0

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, movieId)
        movieTitle = intent.getStringExtra(EXTRA_TITLE)
        moviePoster = intent.getStringExtra(EXTRA_POSTER)
        movieDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        moviePopularity = intent.getDoubleExtra(EXTRA_POPULARITY, .0)
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
        binding.releaseDate.text = movieReleaseDate
        binding.language.text = resources.getString(R.string.language, movieLanguage)

        moviePopularity?.also {
            binding.popularity.text = "P:${it} / C:${movieVoteCount}"
        }

        binding.voteAverage.text = movieVoteAverage.toString()


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