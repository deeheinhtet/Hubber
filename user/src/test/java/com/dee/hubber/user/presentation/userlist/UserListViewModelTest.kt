package com.dee.hubber.user.presentation.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dee.hubber.user.data.remote.dto.GitHubUserDto
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.usecase.GetUsersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel

    @MockK
    private lateinit var getUserUseCase: GetUsersUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserListViewModel(getUserUseCase)
    }


    @Test
    fun `onLoadUser should update UIState with users and success`() = runTest {
        val mockResult = Result.success(
            UsersPageResult(
                users = listOf(
                    GitHubUser(
                        login = "octocat",
                        id = 1,
                        avatarUrl = "https://github.com/images/error/octocat_happy.gif",
                        name = "The Octocat",
                        followers = 100,
                        following = 50,
                        publicRepos = 10,
                        bio = "GitHub mascot",
                        htmlUrl = "https://github.com/octocat"
                    )
                ),
                nextSince = null,
                hasMore = false,
                currentSince = null
            )
        )
        coEvery { getUserUseCase.invoke(any()) } returns mockResult
        viewModel.onLoadUsers(true)
        val uiState = viewModel.uiState.value
        assertThat(uiState.hasError).isEqualTo(null)
        assertThat(uiState.pagination.hasMore).isEqualTo(false)
        assertThat(uiState.users.size).isEqualTo(1)
    }
}