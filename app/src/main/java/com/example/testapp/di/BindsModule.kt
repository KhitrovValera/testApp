package com.example.testapp.di

import com.example.testapp.data.local.source.CoursesLocalDataSource
import com.example.testapp.data.local.source.impl.CoursesLocalDataSourceImpl
import com.example.testapp.data.remote.source.CoursesRemoteDataSource
import com.example.testapp.data.remote.source.impl.CoursesRemoteDataSourceImpl
import com.example.testapp.data.repository.CoursesRepositoryImpl
import com.example.testapp.domain.repository.CoursesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun bindCoursesRemoteDataSource(
        impl: CoursesRemoteDataSourceImpl
    ): CoursesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCoursesLocalDataSource(
        impl: CoursesLocalDataSourceImpl
    ): CoursesLocalDataSource

    @Binds
    @Singleton
    abstract fun bindCoursesRepository(
        impl: CoursesRepositoryImpl
    ): CoursesRepository
}