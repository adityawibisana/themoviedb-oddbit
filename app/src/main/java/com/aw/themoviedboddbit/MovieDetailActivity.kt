package com.aw.themoviedboddbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MovieDetailActivity : AppCompatActivity() {

    var movieId: Int = -1
    var movieTitle: String? = ""
    var moviePoster: String? = ""
    var movieDescription: String? = ""
    var moviePopularity: Double? = .0
    var movieReleaseDate: String? = ""
    var movieBackDrop: String? = ""
    var movieLanguage: String? = ""
    var movieVoteAverage: Double? = .0
    var movieVoteCount: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, movieId)
        movieTitle = intent.getStringExtra(EXTRA_TITLE)
        moviePoster = intent.getStringExtra(EXTRA_POSTER)
        movieDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        moviePopularity = intent.getDoubleExtra(EXTRA_POPULARITY, .0)
        movieReleaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE)
        movieBackDrop = intent.getStringExtra(EXTRA_BACKDROP)
        movieLanguage = intent.getStringExtra(EXTRA_LANGUAGE)
        movieVoteAverage = intent.getDoubleExtra(EXTRA_VOTE_AVERAGE, .0)
        movieVoteCount = intent.getIntExtra(EXTRA_VOTE_COUNT, 0)
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