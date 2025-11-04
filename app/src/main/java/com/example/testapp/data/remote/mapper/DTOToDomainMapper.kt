package com.example.testapp.data.remote.mapper

import com.example.testapp.data.remote.model.CourseDTO
import com.example.testapp.data.remote.model.CoursesResponseDTO
import com.example.testapp.domain.model.Course

fun CourseDTO.toDomain() = Course(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        hasLike = hasLike,
        publishDate = publishDate
    )

fun CoursesResponseDTO.toDomain() = courses.map { it.toDomain() }

