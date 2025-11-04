package com.example.testapp.presentation.home.licked

import com.example.testapp.domain.useCase.EvaluateCourseUseCase
import com.example.testapp.domain.useCase.GetAllLickedCoursesUseCase
import com.example.testapp.presentation.home.common.viewModel.BaseCoursesViewModel
import com.example.testapp.presentation.mapper.ErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LickedViewModel @Inject constructor(
    private val getAllLickedCoursesUseCase: GetAllLickedCoursesUseCase,
    evaluateCourseUseCase: EvaluateCourseUseCase,
    errorMapper: ErrorMapper
) : BaseCoursesViewModel(evaluateCourseUseCase, errorMapper) {

    override suspend fun fetchCourses() = getAllLickedCoursesUseCase()
}
