package com.example.testapp.presentation.mapper

import com.example.testapp.R
import com.example.testapp.domain.common.AppError
import com.example.testapp.presentation.model.UiInfoState
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
    fun mapError(error: AppError): UiInfoState {
        return when (error) {
            is AppError.ApiError -> when (error.code) {
                404 -> UiInfoState(R.string.not_found)
                else -> UiInfoState(R.string.io_error)
            }
            AppError.DatabaseError -> UiInfoState(R.string.db_error)
            AppError.NetworkError -> UiInfoState(R.string.server_error)
            AppError.UnknownError -> UiInfoState(R.string.unknown_error)
        }
    }
}