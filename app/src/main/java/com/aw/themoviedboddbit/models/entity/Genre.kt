package com.aw.themoviedboddbit.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey val id: Int,
    val name: String)