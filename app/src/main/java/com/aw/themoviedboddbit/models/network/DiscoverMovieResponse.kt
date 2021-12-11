package com.aw.themoviedboddbit.models.network

import com.aw.themoviedboddbit.models.NetworkResponseModel
import com.aw.themoviedboddbit.models.entity.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel