package com.aw.themoviedboddbit.di

import com.aw.themoviedboddbit.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UtilsModule {
    @Provides
    @Singleton
    fun provideUtlis() : Utils {
        return Utils()
    }
}