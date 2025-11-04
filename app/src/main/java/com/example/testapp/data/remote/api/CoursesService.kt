package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.model.CoursesResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface CoursesService {

    @Streaming
    @GET("u/{userIndex}/uc")
    suspend fun getCourses(
        @Path("userIndex") userIndex: Int,
        @Query("id") fileId: String,
        @Query("export") exportType: String
    ): CoursesResponseDTO


}

//https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download