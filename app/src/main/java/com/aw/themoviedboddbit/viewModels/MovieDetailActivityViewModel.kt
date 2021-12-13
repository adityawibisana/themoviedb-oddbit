package com.aw.themoviedboddbit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aw.themoviedboddbit.Utils
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.di.NetworkModule
import com.aw.themoviedboddbit.models.entity.Genre
import com.aw.themoviedboddbit.models.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailActivityViewModel @Inject constructor(
    val tmdbService: NetworkModule.TheMovieDBAPIService,
    val genreDao: GenreDao,
    val utils: Utils
): ViewModel() {

    var genre = MutableLiveData<String>()
    var productionCompanies = MutableLiveData<String>()
    var revenue = MutableLiveData<Float>()
    val duration = MutableLiveData<String>()

    fun initialize(movieId: Int) {
        tmdbService.fetchVideos(movieId).enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body()
                movie ?: return

                viewModelScope.launch(Dispatchers.IO) {
                    genre.postValue(
                        utils.listToStringCommas(movie.genres?.map {
                            it.name
                        })
                    )

                    productionCompanies.postValue(
                        utils.listToStringCommas(movie.production_companies?.map {
                            it.name
                        })
                    )

                    revenue.postValue(movie.revenue)
                    duration.postValue(utils.minutesToHourMinute(movie.runtime))
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) { }
        })
    }
}