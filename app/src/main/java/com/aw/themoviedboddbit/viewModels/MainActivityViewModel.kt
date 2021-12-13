package com.aw.themoviedboddbit.viewModels

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aw.themoviedboddbit.db.FavoriteDao
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.di.NetworkModule
import com.aw.themoviedboddbit.models.entity.Favorite
import com.aw.themoviedboddbit.models.entity.Movie
import com.aw.themoviedboddbit.models.network.DiscoverMovieResponse
import com.aw.themoviedboddbit.models.network.GenresResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import java.util.Date as Date

@HiltViewModel
class MainActivityViewModel @Inject constructor(
   val tmdbService: NetworkModule.TheMovieDBAPIService,
   val dao: GenreDao,
   val favoriteDao: FavoriteDao
) : ViewModel() {

   var movieList: MutableLiveData<List<Movie>> = MutableLiveData()
   var unfilteredMovieList = listOf<Movie>()

   init {
      tmdbService.fetchGenres().enqueue(object: Callback<GenresResponse> {
         override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
            response.body()?.genres?.map {
               viewModelScope.launch(Dispatchers.IO) {
                  dao.insertOrReplace(it)
               }
            }
         }

         override fun onFailure(call: Call<GenresResponse>, t: Throwable) { }
      })

       tmdbService.fetchDiscoverMovies().enqueue(object: Callback<DiscoverMovieResponse> {
          override fun onResponse(call: Call<DiscoverMovieResponse>, response: Response<DiscoverMovieResponse>) {
             response.body()?.results?.also {
                movieList.postValue(it)
                unfilteredMovieList = it
             }
          }

          override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) { }
       })
   }

   fun sortByVoteCount() {
      unfilteredMovieList.sortedByDescending { movie ->
         movie.vote_count
      }.also {
         movieList.postValue(it)
      }
   }

   fun sortByPopularity() {
      unfilteredMovieList.sortedByDescending { movie ->
         movie.popularity
      }.also {
         movieList.postValue(it)
      }
   }

   fun sortByReleaseDate() {
      unfilteredMovieList.sortedByDescending { movie ->
         movie.release_date
      }.also {
         movieList.postValue(it)
      }
   }

   fun filterDate(start: Long, end: Long) {
      val cloned = unfilteredMovieList.toList()
      cloned.filter {
         val format = SimpleDateFormat("yyyy-MM-dd")
         var releaseDate: Date? = null
         try {
            releaseDate = format.parse(it.release_date)
         } catch (e: Exception) { }

         if (releaseDate == null) {
            false
         }
         releaseDate!!.time >= start && releaseDate.time <= end
      }.also {
         movieList.postValue(it)
      }
   }

   fun addOrRemoveFromFavorite(id: Int) : Favorite? {
      var favorite = favoriteDao.getValueById(id)
      if (favorite == null) {
         addToFavorite(id)

         favorite = favoriteDao.getValueById(id)
         return favorite
      }

      removeFromFavorite(id)
      return null
   }

   fun addToFavorite(id: Int) {
      favoriteDao.insertOrReplace(Favorite(id, System.currentTimeMillis()))
   }

   fun removeFromFavorite(id: Int) {
      favoriteDao.deleteById(id)
   }
}