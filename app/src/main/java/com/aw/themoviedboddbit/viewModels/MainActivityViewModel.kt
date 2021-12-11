package com.aw.themoviedboddbit.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aw.themoviedboddbit.di.NetworkModule
import com.aw.themoviedboddbit.models.entity.Movie
import com.aw.themoviedboddbit.models.network.DiscoverMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
   val tmdbService: NetworkModule.TheMovieDBAPIService
) : ViewModel() {

   var movieList: MutableLiveData<List<Movie>> = MutableLiveData()

   init {
       tmdbService.fetchDiscoverMovies().enqueue(object: Callback<DiscoverMovieResponse> {
          override fun onResponse(
             call: Call<DiscoverMovieResponse>,
             response: Response<DiscoverMovieResponse>
          ) {
             movieList.postValue(response.body()?.results)
          }

          override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {

          }
       })
   }

}