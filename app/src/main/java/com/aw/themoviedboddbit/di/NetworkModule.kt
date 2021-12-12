package com.aw.themoviedboddbit.di

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.aw.themoviedboddbit.api.RequestInterceptor
import com.aw.themoviedboddbit.models.entity.Movie
import com.aw.themoviedboddbit.models.network.DiscoverMovieResponse
import com.aw.themoviedboddbit.models.network.GenresResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(@NonNull okHttpClient: OkHttpClient): TheMovieDBAPIService {
        return provideRetrofit(okHttpClient).create(TheMovieDBAPIService::class.java)
    }

    interface TheMovieDBAPIService {
        @GET("3/discover/movie")
        fun fetchDiscoverMovies() : Call<DiscoverMovieResponse>

        @GET("3/genre/movie/list")
        fun fetchGenres(@Query("language") language: String = "en-US"): Call<GenresResponse>

        @GET("/3/movie/{movie_id}")
        fun fetchVideos(@Path("movie_id") id: Int): Call<Movie>
    }

}