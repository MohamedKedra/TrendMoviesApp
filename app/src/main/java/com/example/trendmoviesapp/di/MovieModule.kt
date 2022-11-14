package com.example.trendmoviesapp.di

import android.content.Context
import com.example.trendmoviesapp.remote.MovieService
import com.example.trendmoviesapp.repository.MainRepository
import com.example.trendmoviesapp.utils.ConnectionManager
import com.example.trendmoviesapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(Constant.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideRepository(movieService: MovieService) = MainRepository(movieService)

    @Singleton
    @Provides
    fun provideNetwork(@ApplicationContext context: Context) =
        ConnectionManager(context)
}