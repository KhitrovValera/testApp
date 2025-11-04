package com.example.testapp.di

import com.example.testapp.data.remote.api.CoursesService
import com.example.testapp.data.remote.common.ApiCaller
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    fun provideService(
        json: Json
    ) : CoursesService {
        return Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(CoursesService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiCaller() = ApiCaller()

}