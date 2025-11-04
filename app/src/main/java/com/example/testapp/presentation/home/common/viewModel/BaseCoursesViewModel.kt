package com.example.testapp.presentation.home.common.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.common.Resource
import com.example.testapp.domain.model.Course
import com.example.testapp.domain.useCase.EvaluateCourseUseCase
import com.example.testapp.presentation.mapper.ErrorMapper
import com.example.testapp.presentation.model.UiInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseCoursesViewModel(
    private val evaluateCourseUseCase: EvaluateCourseUseCase,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    protected val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    protected val _actions = MutableSharedFlow<Action>()
    val actions: SharedFlow<Action> = _actions
    protected abstract suspend fun fetchCourses(): Resource<List<Course>>

    init {
        loadCourses()
    }

    fun loadCourses() {
        _state.value = State.Loading
        viewModelScope.launch {
            val result = fetchCourses()

            _state.value = when (result) {
                is Resource.Error -> State.Error(errorMapper.mapError(result.error))
                is Resource.PartialSuccess -> {
                    val error = errorMapper.mapError(result.error)
                    State.Success(CoursesState(result.data, error))
                }
                is Resource.Success -> State.Success(CoursesState(result.data))
            }
        }
    }

    fun onCourseClicked(courseId: Int) {
        viewModelScope.launch {
            _actions.emit(Action.RouteToDetail(courseId))
        }
    }

    fun onLikeClicked(courseId: Int) {
        val currentState = _state.value
        if (currentState !is State.Success) return

        viewModelScope.launch {
            val result = evaluateCourseUseCase(courseId)
            if (result is Resource.Success) {
                val updatedCourse = result.data
                val updatedCourses = currentState.courses.courses.map { course ->
                    if (course.id == courseId) updatedCourse else course
                }

                _state.value = State.Success(
                    currentState.courses.copy(courses = updatedCourses)
                )
            }
        }
    }

    sealed interface Action {
        data class RouteToDetail(val courseId: Int) : Action
    }

    sealed interface State {
        object Loading : State
        data class Success(val courses: CoursesState) : State
        data class Error(val error: UiInfoState) : State
    }

    data class CoursesState(
        val courses: List<Course>,
        val error: UiInfoState? = null
    )
}
