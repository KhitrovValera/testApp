package com.example.testapp.data.local.source

import com.example.testapp.data.local.entity.CourseEntity

interface CoursesLocalDataSource {
    suspend fun insertCourses(courses: List<CourseEntity>)

    suspend fun getAllCourses() : List<CourseEntity>

    suspend fun getAllLickedCourses() : List<CourseEntity>

    suspend fun getCourseById(courseId: Int) : CourseEntity

    suspend fun updateCourse(course: CourseEntity)
}
