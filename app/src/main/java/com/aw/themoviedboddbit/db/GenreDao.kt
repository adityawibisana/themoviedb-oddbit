package com.aw.themoviedboddbit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.aw.themoviedboddbit.models.entity.Genre

@Dao
abstract class GenreDao : BaseDao<Genre> {
    @Query("SELECT `name` FROM Genre WHERE `id` = :id ")
    abstract fun get(id: Int): LiveData<String?>
}