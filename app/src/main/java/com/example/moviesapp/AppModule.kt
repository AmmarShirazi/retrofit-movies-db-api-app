package com.example.moviesapp

import com.example.moviesapp.movieservice.FreeMoviesDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFreeMoviesDatabaseRepository(): FreeMoviesDatabaseRepository {
        return FreeMoviesDatabaseRepository()
    }
}
