package com.example.testapp.presentation.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.common.Resource
import com.example.testapp.domain.model.Course
import com.example.testapp.domain.useCase.EvaluateCourseUseCase
import com.example.testapp.domain.useCase.GetCourseByIdUseCase
import com.example.testapp.presentation.home.common.viewModel.BaseCoursesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val evaluateCourseUseCase: EvaluateCourseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId = savedStateHandle.get<Int>("courseId")

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        loadCourseById()
    }

    fun loadCourseById() {
        if (courseId != null) {
            viewModelScope.launch {
                val localResult = getCourseByIdUseCase(courseId)
                _state.value = when (localResult) {
                    is Resource.Error -> State.Loading
                    is Resource.PartialSuccess -> State.Loading
                    is Resource.Success -> State.Success(localResult.data)
                }
            }
        }
    }

    fun onLikeClicked() {
        val currentState = _state.value
        if (currentState !is State.Success || courseId == null) return

        viewModelScope.launch {
            val result = evaluateCourseUseCase(courseId)
            if (result is Resource.Success) {
                val updatedCourse = result.data

                _state.value = State.Success(
                    updatedCourse
                )
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Success(val course: Course) : State
    }


}