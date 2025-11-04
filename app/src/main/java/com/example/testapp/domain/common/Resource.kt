package com.example.testapp.domain.common

sealed class Resource<out T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val error: AppError): Resource<Nothing>()
    data class PartialSuccess<T>(val data: T, val error: AppError): Resource<T>()
}