package com.dee.hubber.user.presentation.settings

import androidx.lifecycle.viewModelScope
import com.dee.common.AppSharedPreference
import com.dee.core_network.base.NetworkException
import com.dee.core_ui.base.BaseViewModel
import com.dee.hubber.user.domain.usecase.GetCurrentUserDetailsUseCase
import com.dee.hubber.user.domain.usecase.GetCurrentUserRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val usersDetailsUseCase: GetCurrentUserDetailsUseCase,
    private val getUserRepositoryUseCase: GetCurrentUserRepositoryUseCase,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {


    private val _uiState = MutableStateFlow(SettingsUiState())

    val uiState: StateFlow<SettingsUiState>
        get() = _uiState

    fun onLoadData() {
        loadUserDetails()
        loadUserRepository()
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            showLoading()
            val result = usersDetailsUseCase.invoke(Unit)
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

    private fun loadUserRepository() {
        viewModelScope.launch {
            showLoading()
            val result = getUserRepositoryUseCase.invoke(Unit)
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

    fun onDoLogout() {
        appSharedPreference.token = null
        emitEvent(SettingsEvents.onLogoutSuccess)
    }
}
