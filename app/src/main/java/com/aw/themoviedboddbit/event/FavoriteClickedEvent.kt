package com.aw.themoviedboddbit.event

import com.aw.themoviedboddbit.models.entity.Movie

data class FavoriteClickedEvent(val movie: Movie)