package com.example.testapp.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.testapp.domain.useCase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    val _email = MutableStateFlow("")
    private val email: StateFlow<String> = _email

    val _password = MutableStateFlow("")
    private val password: StateFlow<String> = _password

    private val _actions = MutableSharedFlow<Action>()
    val actions: SharedFlow<Action> = _actions


    suspend fun signIn() {
        if (signInUseCase(email.value, password.value)) {
            _actions.emit(Action.SignIn)
        }
    }

    sealed interface Action {
        object SignIn : Action
    }
}