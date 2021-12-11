package com.aw.themoviedboddbit.models.network

import com.aw.themoviedboddbit.models.NetworkResponseModel
import com.aw.themoviedboddbit.models.entity.Genre

data class GenresResponse(
    val genres: List<Genre>,
) : NetworkResponseModel