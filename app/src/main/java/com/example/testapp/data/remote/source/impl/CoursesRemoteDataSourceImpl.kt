package com.example.testapp.data.remote.source.impl

import com.example.testapp.data.remote.api.CoursesService
import com.example.testapp.data.remote.common.ApiCaller
import com.example.testapp.data.remote.common.NetworkResult
import com.example.testapp.data.remote.model.CoursesResponseDTO
import com.example.testapp.data.remote.source.CoursesRemoteDataSource
import javax.inject.Inject

class CoursesRemoteDataSourceImpl @Inject constructor(
    private val api: CoursesService,
    private val apiCaller: ApiCaller
): CoursesRemoteDataSource {

    override suspend fun getCourses(): NetworkResult<CoursesResponseDTO> {
        return apiCaller.safeApiCall { api.getCourses(
            USER_INDEX,
            FILED_ID,
            EXPORT_TYPE
        ) }
    }

    override suspend fun getLikedCourses(): NetworkResult<CoursesResponseDTO> {
        return apiCaller.safeApiCall { CoursesResponseDTO(
            api.getCourses(
                USER_INDEX,
                FILED_ID,
                EXPORT_TYPE
            ).courses.filter { it.hasLike }
        ) }
    }

    companion object {
        private const val USER_INDEX = 0
        private const val FILED_ID = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q"
        private const val EXPORT_TYPE = "download"
    }
}