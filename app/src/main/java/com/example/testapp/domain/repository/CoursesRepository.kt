package com.example.testapp.domain.repository

import com.example.testapp.domain.common.Resource
import com.example.testapp.domain.model.Course

interface CoursesRepository {
    suspend fun getAllCourses(): Resource<List<Course>>

    suspend fun getAllLickedCourses(): Resource<List<Course>>

    suspend fun getCourseById(id: Int): Resource<Course>

    suspend fun evaluateCourse(id: Int): Resource<Course>

    suspend fun signIn(email: String, password: String): Boolean

}
