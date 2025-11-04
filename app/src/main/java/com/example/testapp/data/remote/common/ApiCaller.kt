package com.example.testapp.data.remote.common

import com.example.testapp.domain.common.AppError
import okio.IOException
import retrofit2.HttpException

class ApiCaller {
    suspend fun <T> safeApiCall(
        call: suspend () -> T
    ) : NetworkResult<T> {
        return try {
            NetworkResult.Success(call())
        } catch (e: HttpException) {
            NetworkResult.Error(AppError.ApiError(e.code()))
        } catch (_: IOException) {
            NetworkResult.Error(AppError.NetworkError)
        } catch (_: Exception) {
            NetworkResult.Error(AppError.UnknownError)
        }
    }
}