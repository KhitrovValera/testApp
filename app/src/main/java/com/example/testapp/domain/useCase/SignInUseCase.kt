package com.example.testapp.domain.useCase

import com.example.testapp.domain.repository.CoursesRepository

class SignInUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.signIn(email, password)
    }
}