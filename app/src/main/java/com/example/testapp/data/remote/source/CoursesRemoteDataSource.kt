package com.example.testapp.data.remote.source

import com.example.testapp.data.remote.common.NetworkResult
import com.example.testapp.data.remote.model.CoursesResponseDTO

interface CoursesRemoteDataSource {
    suspend fun getCourses() : NetworkResult<CoursesResponseDTO>

    suspend fun getLikedCourses(): NetworkResult<CoursesResponseDTO>

}