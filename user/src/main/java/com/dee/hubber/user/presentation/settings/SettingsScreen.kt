package com.dee.hubber.user.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dee.core_ui.base.AppEvent
import com.dee.core_ui.base.BaseScreen
import com.dee.hubber.user.R
import com.dee.hubber.user.presentation.settings.navigation.SettingsNavigationActions
import com.dee.hubber.user.presentation.userdetails.components.UserDetailsHeader
import com.dee.hubber.user.presentation.userdetails.components.UserRepositoryItem
import com.dee.hubber.user.presentation.userlist.components.EmptyUserItem

@Composable
fun SettingsScreen(
    navigationActions: SettingsNavigationActions,
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onLoadData()
    }

    fun onHandleEvent(event: AppEvent) {
        if (event == SettingsEvents.onLogoutSuccess) {
            navigationActions.onLogout()
        }
    }

    BaseScreen(viewModel = viewModel, onHandleEvent = {
        onHandleEvent(it)
    }) {
        SettingsContent(
            uiState = uiState,
            onRetry = {
                viewModel.onLoadData()
            },
            onLogout = {
                viewModel.onDoLogout()
            },
            onRepoClicked = {
                navigationActions.onViewUserGithub(it, context)
            },
            onViewGithubUser = {
                navigationActions.onViewUserGithub(it, context)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    uiState: SettingsUiState = SettingsUiState(),
    onRetry: () -> Unit = {},
    onLogout: () -> Unit = {},
    onRepoClicked: (url: String) -> Unit = {},
    onViewGithubUser: (url: String) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            LargeTopAppBar(
                collapsedHeight = 80.dp,
                expandedHeight = 200.dp,
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    uiState.user?.let { user ->
                        UserDetailsHeader(
                            user,
                            onViewGithubUser = {
                                onViewGithubUser(user.htmlUrl.orEmpty())
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = paddingValues
            ) {
                items(uiState.repositories) { repo ->
                    UserRepositoryItem(repo) {
                        if (repo.repoUrl.isNotEmpty()) {
                            onRepoClicked(repo.repoUrl)
                        }
                    }
                    if (repo != uiState.repositories.last()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                        )
                    }
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                onClick = {
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    stringResource(R.string.label_logout),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            uiState.error?.let {
                EmptyUserItem(errorMessage = uiState.error, onRetry = {
                    onRetry()
                })
            }
        }

    }
}