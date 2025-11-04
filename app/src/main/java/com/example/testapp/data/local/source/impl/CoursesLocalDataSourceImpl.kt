package com.example.testapp.data.local.source.impl

import com.example.testapp.data.local.dao.CoursesDao
import com.example.testapp.data.local.entity.CourseEntity
import com.example.testapp.data.local.source.CoursesLocalDataSource
import javax.inject.Inject

class CoursesLocalDataSourceImpl @Inject constructor(
    private val courseDao: CoursesDao
) : CoursesLocalDataSource {
    override suspend fun insertCourses(courses: List<CourseEntity>) {
        courseDao.insertCourses(courses)
    }

    override suspend fun getAllCourses(): List<CourseEntity> {
        return courseDao.getAllCourses()
    }

    override suspend fun getAllLickedCourses(): List<CourseEntity> {
        return courseDao.getAllLickedCourses()
    }

    override suspend fun getCourseById(courseId: Int): CourseEntity {
        return courseDao.getCourseById(courseId)
    }

    override suspend fun updateCourse(course: CourseEntity) {
        return courseDao.insertCourse(course.copy(hasLike = !course.hasLike))
    }
}