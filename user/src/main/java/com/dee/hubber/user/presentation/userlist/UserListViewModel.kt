package com.dee.hubber.user.presentation.userlist

import androidx.lifecycle.viewModelScope
import com.dee.core_network.base.NetworkException
import com.dee.core_ui.base.BaseViewModel
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState())

    val uiState: StateFlow<UserListUiState>
        get() = _uiState

    private var _query = MutableStateFlow("")

    private var searchQueryJob: Job? = null

    init {
        onLoadUsers()
    }

    fun onSearchUser(query: String) {
        if (searchQueryJob != null) {
            searchQueryJob?.cancel()
            searchQueryJob = null
        }
        searchQueryJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _query.value = query
            onLoadUsers(refresh = true)
        }
    }

    fun onLoadUsers(
        refresh: Boolean = false
    ) {
        viewModelScope.launch {
            if (refresh) {
                _uiState.value = UserListUiState(
                    pagination = UsersPaginationState(isLoading = true),
                    hasError = null
                )
            } else if (_uiState.value.users.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    pagination = _uiState.value.pagination.copy(isLoading = true, error = null)
                )
            }
            viewModelScope.launch {
                showLoading()
                val result = getUserUseCase.invoke(
                    params = GithubUserRequest(
                        since = if (refresh) null else _uiState.value.pagination.since,
                        query = _query.value,
                        page = uiState.value.pagination.searchPage
                    )
                )
                handleUsersResult(result, refresh)
            }
        }
    }

    private fun handleUsersResult(
        result: Result<UsersPageResult>,
        refresh: Boolean,
        isLoadingMore: Boolean = false
    ) {
        if (!isLoadingMore) {
            hideLoading()
        }
        when {
            result.isSuccess -> {
                val pageResult = result.getOrNull()
                val currentUsers = if (refresh) emptyList() else _uiState.value.users
                val newUsers = currentUsers + pageResult?.users.orEmpty()

                _uiState.value = _uiState.value.copy(
                    users = newUsers,
                    pagination = UsersPaginationState(
                        since = pageResult?.nextSince,
                        hasMore = pageResult?.hasMore ?: false,
                        isLoading = false,
                        isLoadingMore = false,
                        error = null,
                        totalLoaded = newUsers.size,
                        searchPage = if (_query.value.isNotEmpty()) _uiState.value.pagination.searchPage + 1 else 1,
                    )
                )
            }

            result.isFailure -> {
                val exception = result.exceptionOrNull() as? NetworkException

                if (isLoadingMore) {
                    _uiState.value = _uiState.value.copy(
                        pagination = _uiState.value.pagination.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            error = exception?.message ?: "Failed to load users"
                        )
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        hasError = exception?.message.orEmpty()
                    )
                }
            }
        }
    }


    /**
     * Load next page of users
     */
    fun loadNextPage() {
        val currentPagination = _uiState.value.pagination

        if (!currentPagination.hasMore ||
            currentPagination.isLoading ||
            currentPagination.isLoadingMore ||
            currentPagination.since == null
        ) {
            return
        }

        _uiState.value = _uiState.value.copy(
            pagination = currentPagination.copy(isLoadingMore = true, error = null)
        )

        viewModelScope.launch {
            val result = getUserUseCase.invoke(
                params = GithubUserRequest(
                    since = currentPagination.since,
                    query = _query.value,
                    page = uiState.value.pagination.searchPage
                )
            )
            handleUsersResult(result, refresh = false, isLoadingMore = true)
        }
    }
}