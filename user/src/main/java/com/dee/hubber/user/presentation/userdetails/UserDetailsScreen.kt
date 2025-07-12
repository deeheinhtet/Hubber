package com.dee.hubber.user.presentation.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dee.core_ui.base.BaseScreen
import com.dee.hubber.user.presentation.userdetails.components.UserDetailsHeader
import com.dee.hubber.user.presentation.userdetails.components.UserRepositoryItem
import com.dee.hubber.user.presentation.userlist.components.EmptyUserItem
import com.dee.hubber.user.presentation.userdetails.navigation.UserDetailsNavigationActions


@Composable
fun UserDetailsScreen(
    navigationActions: UserDetailsNavigationActions,
    username: String = "",
    viewModel: UserDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onLoadData(username)
    }

    BaseScreen(viewModel = viewModel, modifier = Modifier) {
        UserDetailsContent(
            uiState = uiState,
            onBackPressed = {
                navigationActions.onPopBackFromDetails()
            }, onRepoClicked = {
                navigationActions.onViewUserRepository(it, context)
            },
            onRetry = {
                viewModel.onLoadData(username)
            },
            onViewGithubUser = {
                navigationActions.onViewGithubUser(it, context)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsContent(
    onBackPressed: () -> Unit = {},
    onRepoClicked: (url: String) -> Unit = {},
    onViewGithubUser: (url: String) -> Unit = {},
    onRetry: () -> Unit = {},
    uiState: UserDetailsUiState = UserDetailsUiState()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "BackArrow",
                        )
                    }
                },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
        uiState.error?.let {
            EmptyUserItem(errorMessage = uiState.error, onRetry = {
                onRetry()
            })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewUserDetailsContent() {
    UserDetailsContent(
        uiState = UserDetailsUiState()
    )
}
