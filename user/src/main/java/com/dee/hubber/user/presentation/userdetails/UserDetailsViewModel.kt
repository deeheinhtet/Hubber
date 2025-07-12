package com.dee.hubber.user.presentation.userdetails

import androidx.lifecycle.viewModelScope
import com.dee.core_network.base.NetworkException
import com.dee.core_ui.base.BaseViewModel
import com.dee.hubber.user.domain.usecase.GetUserRepositoryUseCase
import com.dee.hubber.user.domain.usecase.GetUsersDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsUseCase: GetUsersDetailsUseCase,
    private val getUserRepositoryUseCase: GetUserRepositoryUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UserDetailsUiState())

    val uiState: StateFlow<UserDetailsUiState>
        get() = _uiState

    fun loadUserDetails(username: String) {
        viewModelScope.launch {
            showLoading()
            val result = userDetailsUseCase.invoke(username)
            when {
                result.isSuccess -> {
                    hideLoading()
                    _uiState.value = _uiState.value.copy(user = result.getOrNull())
                }

                result.isFailure -> {
                    hideLoading()
                    val exception = result.exceptionOrNull() as? NetworkException
                    _uiState.value = _uiState.value.copy(error = exception?.message)
                }
            }
        }
    }

    fun loadUserRepository(username: String) {
        viewModelScope.launch {
            showLoading()
            val result = getUserRepositoryUseCase.invoke(username)
            when {
                result.isSuccess -> {
                    hideLoading()
                    _uiState.value =
                        _uiState.value.copy(repositories = result.getOrNull().orEmpty())
                }

                result.isFailure -> {
                    hideLoading()
                    val exception = result.exceptionOrNull() as? NetworkException
                    _uiState.value = _uiState.value.copy(error = exception?.message)
                }
            }
        }
    }

    fun onLoadData(username: String) {
        loadUserDetails(username)
        loadUserRepository(username)
    }

}