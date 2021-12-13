package com.aw.themoviedboddbit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.aw.themoviedboddbit.db.AppDatabase
import com.aw.themoviedboddbit.models.entity.Favorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
public class DBTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.databaseBuilder(context, AppDatabase::class.java, "test")
        .allowMainThreadQueries()
        .build()
    val favoriteDao = db.favoriteDao()

    @Test
    fun insertGetFavoriteTest() = runBlocking {
        val dateAdded = System.currentTimeMillis()
        favoriteDao.insertOrReplace(Favorite(1, dateAdded))

        val favorite = favoriteDao.getValueById(1)
        assertEquals(favorite!!.added, dateAdded)
    }
}