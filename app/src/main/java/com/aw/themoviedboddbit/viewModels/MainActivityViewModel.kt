package com.aw.themoviedboddbit.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aw.themoviedboddbit.db.GenreDao
import com.aw.themoviedboddbit.di.NetworkModule
import com.aw.themoviedboddbit.models.entity.Movie
import com.aw.themoviedboddbit.models.network.DiscoverMovieResponse
import com.aw.themoviedboddbit.models.network.GenresResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
   val tmdbService: NetworkModule.TheMovieDBAPIService,
   val dao: GenreDao
) : ViewModel() {

   var movieList: MutableLiveData<List<Movie>> = MutableLiveData()

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
             movieList.postValue(response.body()?.results)
          }

          override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) { }
       })
   }

   fun sortByVoteCount() {
      movieList.value?.sortedByDescending { movie ->
         movie.vote_count
      }?.also {
         movieList.postValue(it)
      }
   }

}