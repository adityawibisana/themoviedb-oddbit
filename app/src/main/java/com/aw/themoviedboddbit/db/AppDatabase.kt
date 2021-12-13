package com.aw.themoviedboddbit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aw.themoviedboddbit.models.entity.Favorite
import com.aw.themoviedboddbit.models.entity.Genre

@Database(entities = arrayOf(Genre::class, Favorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao() : GenreDao
    abstract fun favoriteDao() : FavoriteDao
}