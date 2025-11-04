package com.example.testapp.data.local.maapper

import com.example.testapp.data.local.entity.CourseEntity
import com.example.testapp.domain.model.Course

fun CourseEntity.toDomain() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun List<CourseEntity>.toDomain() = map { it.toDomain() }