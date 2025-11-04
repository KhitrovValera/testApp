package com.example.testapp.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponseDTO(
    @SerialName("courses")
    val courses: List<CourseDTO>
)