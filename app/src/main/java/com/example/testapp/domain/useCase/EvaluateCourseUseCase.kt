package com.example.testapp.domain.useCase

import com.example.testapp.domain.common.Resource
import com.example.testapp.domain.model.Course
import com.example.testapp.domain.repository.CoursesRepository

class EvaluateCourseUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(courseId: Int): Resource<Course> {
        return repository.evaluateCourse(courseId)
    }
}