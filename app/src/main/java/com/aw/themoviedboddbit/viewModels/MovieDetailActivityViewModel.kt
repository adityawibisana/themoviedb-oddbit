package com.aw.themoviedboddbit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aw.themoviedboddbit.Utils
import com.aw.themoviedboddbit.db.FavoriteDao
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.di.NetworkModule
import com.aw.themoviedboddbit.models.entity.Favorite
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
    val utils: Utils,
    val favoriteDao: FavoriteDao
): ViewModel() {

    var genre = MutableLiveData<String>()
    var productionCompanies = MutableLiveData<String>()
    var revenue = MutableLiveData<Float>()
    val duration = MutableLiveData<String>()
    val homepage = MutableLiveData<String>()
    val favorited = MutableLiveData<Boolean>()

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
                    homepage.postValue(if (movie.homepage.isNullOrEmpty()) "-" else movie.homepage)

                    favorited.postValue(favoriteDao.getValueById(movieId) != null)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) { }
        })
    }

    fun addOrRemoveFromFavorite(id: Int) : Favorite? {
        var favorite = favoriteDao.getValueById(id)
        if (favorite == null) {
            addToFavorite(id)

            favorite = favoriteDao.getValueById(id)
            favorited.postValue(true)

            return favorite
        }

        removeFromFavorite(id)
        favorited.postValue(false)
        return null
    }

    fun addToFavorite(id: Int) {
        favoriteDao.insertOrReplace(Favorite(id, System.currentTimeMillis()))
    }

    fun removeFromFavorite(id: Int) {
        favoriteDao.deleteById(id)
    }
}