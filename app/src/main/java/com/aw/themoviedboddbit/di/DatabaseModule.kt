package com.aw.themoviedboddbit.di

import android.content.Context
import androidx.room.Room
import com.aw.themoviedboddbit.db.AppDatabase
import com.aw.themoviedboddbit.db.BaseDao
import com.aw.themoviedboddbit.db.FavoriteDao
import com.aw.themoviedboddbit.db.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MainThreadDB {
    @Provides
    @Singleton
    fun provideDatabaseSingleton(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "tmdb-main")
            .build()
    }

    @Provides
    @Singleton
    fun provideGenreDao(database: AppDatabase): GenreDao {
        return database.genreDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

}