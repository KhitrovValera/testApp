package com.example.testapp.data.remote.common

import com.example.testapp.domain.common.AppError

sealed class NetworkResult<out T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error(val error: AppError) : NetworkResult<Nothing>()
}