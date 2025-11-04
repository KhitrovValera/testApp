package com.example.testapp.data.repository

import android.util.Log
import com.example.testapp.data.local.entity.CourseEntity
import com.example.testapp.data.local.maapper.toDomain
import com.example.testapp.data.local.maapper.toEntity
import com.example.testapp.data.local.source.CoursesLocalDataSource
import com.example.testapp.data.remote.common.NetworkResult
import com.example.testapp.data.remote.mapper.toDomain
import com.example.testapp.data.remote.model.CoursesResponseDTO
import com.example.testapp.data.remote.source.CoursesRemoteDataSource
import com.example.testapp.domain.common.AppError
import com.example.testapp.domain.common.Resource
import com.example.testapp.domain.model.Course
import com.example.testapp.domain.repository.CoursesRepository
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val localDataSource: CoursesLocalDataSource,
    private val remoteDataSource: CoursesRemoteDataSource
) : CoursesRepository {

    private var first = true

    override suspend fun getAllCourses(): Resource<List<Course>> {
        return if (first) {
            first = false
            getCourses(
                remoteDataSource::getCourses,
                localDataSource::getAllCourses
            )
        } else {
            getCoursesFromDatabase(
                AppError.UnknownError,
                localDataSource::getAllCourses
            )
        }
    }

    override suspend fun getAllLickedCourses(): Resource<List<Course>> {
//        return getCourses(
//            remoteDataSource::getLikedCourses,
//            localDataSource::getAllLickedCourses
//        )
        return getCoursesFromDatabase(
            AppError.UnknownError,
            localDataSource::getAllLickedCourses
        )
    }

    override suspend fun getCourseById(id: Int): Resource<Course> {
        val localResult = localDataSource.getCourseById(id).toDomain()
        return Resource.Success(localResult)
    }

    override suspend fun evaluateCourse(id: Int): Resource<Course> {
        val localResult = localDataSource.getCourseById(id)
        localDataSource.updateCourse(localResult)
        return Resource.Success(localDataSource.getCourseById(id).toDomain())
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val isEmailValid = emailRegex.matches(email)
        val isPasswordNotEmpty = password.isNotBlank()
        return isEmailValid && isPasswordNotEmpty
    }

    suspend fun getCourses(
        getCoursesRemote: suspend () -> NetworkResult<CoursesResponseDTO>,
        getCoursesLocal: suspend () -> List<CourseEntity>
    ): Resource<List<Course>> {
        val remoteResult = getCoursesRemote()
        Log.d("remoteResult", remoteResult.toString())
        return when (remoteResult) {
            is NetworkResult.Success -> {
                Log.d("remoteResult", remoteResult.data.courses.toString())
                val courses = remoteResult.data.toDomain()
                localDataSource.insertCourses(courses.toEntity())
                Resource.Success(courses)
            }
            is NetworkResult.Error -> {
                getCoursesFromDatabase(
                    remoteResult.error,
                    getCoursesLocal
                )
            }
        }
    }

    suspend fun getCoursesFromDatabase(
        appError: AppError,
        getCourses: suspend () -> List<CourseEntity>
    ): Resource<List<Course>> {
        return try {
            val localResult = getCourses()
            if (localResult.isNotEmpty()) {
                Resource.PartialSuccess(localResult.toDomain(), appError)
            } else {
                Resource.Error(appError)
            }
        } catch (_: Exception) {
            Resource.Error(AppError.DatabaseError)
        }
    }
}