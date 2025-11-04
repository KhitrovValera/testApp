package com.example.testapp.data.local.maapper

import com.example.testapp.data.local.entity.CourseEntity
import com.example.testapp.domain.model.Course

fun Course.toEntity() = CourseEntity(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        hasLike = hasLike,
        publishDate = publishDate,
    )

fun List<Course>.toEntity() = map { it.toEntity() }