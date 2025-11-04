package com.example.testapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.local.dao.CoursesDao
import com.example.testapp.data.local.entity.CourseEntity

@Database(
    entities = [
        CourseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CoursesDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
}