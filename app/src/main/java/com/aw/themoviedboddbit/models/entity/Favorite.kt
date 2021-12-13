package com.aw.themoviedboddbit.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "dateAdded")
    val added: Long)