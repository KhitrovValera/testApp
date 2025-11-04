package com.example.testapp.domain.useCase

import com.example.testapp.domain.repository.CoursesRepository

class GetAllCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke() = repository.getAllCourses()
}