package com.example.testapp.domain.useCase

import com.example.testapp.domain.repository.CoursesRepository

class GetCourseByIdUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCourseById(id)

}