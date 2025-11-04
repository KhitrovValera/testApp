package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.data.local.dao.CoursesDao
import com.example.testapp.data.local.database.CoursesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "courses_db"

    @Singleton
    @Provides
    fun provideDao(
        coursesDatabase: CoursesDatabase
    ): CoursesDao {
        return coursesDatabase.coursesDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoursesDatabase {
        return Room.databaseBuilder(
            context,
            CoursesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


}