package com.dee.hubber.user.presentation.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dee.core_ui.base.AppEvent
import com.dee.core_ui.base.BaseScreen
import com.dee.hubber.user.presentation.userlist.components.EmptyUserItem
import com.dee.hubber.user.presentation.userlist.components.LoadingMoreItem
import com.dee.hubber.user.presentation.userlist.components.UserListToolbar
import com.dee.hubber.user.presentation.userlist.components.UserRowItem
import com.dee.hubber.user.presentation.userlist.navigation.UserListNavigationActions
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun UserListScreen(
    navigationActions: UserListNavigationActions,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.users.size - 5 &&
                    uiState.pagination.hasMore &&
                    !uiState.pagination.isLoadingMore &&
                    !uiState.pagination.isLoading
                ) {
                    viewModel.loadNextPage()
                }
            }
    }

    fun onHandleEvents(event: AppEvent) {
        when (event) {
            is UserListEvents.OnUserListLoadFailed -> {}
            is UserListEvents.OnNavigateToUserDetails -> {
                navigationActions.onNavigateUserDetails(event.name)
            }
        }
    }

    BaseScreen(
        viewModel = viewModel,
        onHandleEvent = {
            onHandleEvents(it)
        }) {
        Column {
            UserListToolbar(
                onSearchTextChange = {
                    viewModel.onSearchUser(it)
                }
            )
            UserListContent(
                uiState = uiState, state = lazyListState,
                onRetryLoadUser = {
                    viewModel.onLoadUsers(refresh = true)
                },
                onClickUserItem = {
                    viewModel.emitEvent(UserListEvents.OnNavigateToUserDetails(it))
                }
            )
        }
    }
}


@Composable
fun UserListContent(
    uiState: UserListUiState = UserListUiState(),
    state: LazyListState,
    onClickUserItem: (name: String) -> Unit = {},
    onRetryLoadUser: () -> Unit = {},

    ) {
    val users = uiState.users
    if (uiState.hasError != null) {
        EmptyUserItem(
            uiState.hasError,
            onRetry = {
                onRetryLoadUser()
            })
    } else {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                users,
                key = { it.id },
            ) { user ->
                UserRowItem(
                    modifier = Modifier
                        .clickable { onClickUserItem(user.login) }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    name = user.login,
                    imageUrl = user.avatarUrl,
                )
            }
            if (uiState.pagination.isLoadingMore) {
                item {
                    LoadingMoreItem()
                }
            }
        }
    }
}