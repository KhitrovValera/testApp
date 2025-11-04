package com.example.testapp.di

import com.example.testapp.domain.repository.CoursesRepository
import com.example.testapp.domain.useCase.EvaluateCourseUseCase
import com.example.testapp.domain.useCase.GetAllCoursesUseCase
import com.example.testapp.domain.useCase.GetAllLickedCoursesUseCase
import com.example.testapp.domain.useCase.GetCourseByIdUseCase
import com.example.testapp.domain.useCase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetAllCoursesUseCase(
        repository: CoursesRepository
    ) = GetAllCoursesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllLickedCoursesUseCase(
        repository: CoursesRepository
    ) = GetAllLickedCoursesUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInUseCase(
        repository: CoursesRepository
    ) = SignInUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCourseByIdUseCase(
        repository: CoursesRepository
    ) = GetCourseByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideEvaluateCourseUseCase(
        repository: CoursesRepository
    ) = EvaluateCourseUseCase(repository)
}