package com.example.testapp.domain.useCase

import com.example.testapp.domain.repository.CoursesRepository

class GetAllLickedCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke() = repository.getAllLickedCourses()
}