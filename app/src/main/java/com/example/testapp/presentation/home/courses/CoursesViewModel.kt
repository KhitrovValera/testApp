package com.example.testapp.presentation.home.courses

import com.example.testapp.domain.useCase.EvaluateCourseUseCase
import com.example.testapp.domain.useCase.GetAllCoursesUseCase
import com.example.testapp.presentation.home.common.viewModel.BaseCoursesViewModel
import com.example.testapp.presentation.mapper.ErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    evaluateCourseUseCase: EvaluateCourseUseCase,
    errorMapper: ErrorMapper
) : BaseCoursesViewModel(evaluateCourseUseCase, errorMapper) {

    private var sorted = true

    override suspend fun fetchCourses() = getAllCoursesUseCase()

    fun onSortClicked() {
        val currentState = _state.value
        if (currentState !is State.Success) return

        _state.value = State.Success(CoursesState(
            if (sorted) {
                currentState.courses.courses.sortedByDescending { it.publishDate }
            } else {
                currentState.courses.courses.sortedBy { it.publishDate }
            }
        ))
        sorted = !sorted
    }
}
