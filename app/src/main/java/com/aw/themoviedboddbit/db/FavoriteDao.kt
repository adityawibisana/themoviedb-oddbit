package com.aw.themoviedboddbit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.aw.themoviedboddbit.models.entity.Favorite

@Dao
abstract class FavoriteDao : BaseDao<Favorite> {
    @Query("SELECT * FROM Favorite WHERE `id` = :id")
    abstract fun getById(id: Int): LiveData<Favorite>?

    @Query("SELECT * FROM Favorite WHERE `id` = :id")
    abstract fun getValueById(id: Int): Favorite?

    @Query("DELETE FROM Favorite WHERE `id` = :id")
    abstract fun deleteById(id: Int)
}