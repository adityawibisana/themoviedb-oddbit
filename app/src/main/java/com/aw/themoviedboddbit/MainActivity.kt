package com.aw.themoviedboddbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aw.themoviedboddbit.di.NetworkModule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var api: NetworkModule.TheMovieDBAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}