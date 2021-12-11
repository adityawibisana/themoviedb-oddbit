package com.aw.themoviedboddbit.di

import com.aw.themoviedboddbit.models.network.DiscoverMovieResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(): TheMovieDBAPIService {
        return retrofit.create(TheMovieDBAPIService::class.java)
    }

    interface TheMovieDBAPIService {
        @GET("3/discover/movie")
        fun fetchDiscoverMovies(@Query("api_key") apiKey: String) : Call<DiscoverMovieResponse>
    }

}