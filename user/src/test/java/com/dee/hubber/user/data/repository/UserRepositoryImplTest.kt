package com.dee.hubber.user.data.repository

import com.dee.common.Constants
import com.dee.core_network.base.NetworkException
import com.dee.hubber.user.data.remote.api.UserApiService
import com.dee.hubber.user.data.remote.dto.GitHubUserDto
import com.dee.hubber.user.domain.model.GithubUserRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import com.google.common.truth.Truth.assertThat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    @MockK
    private lateinit var apiService: UserApiService

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(apiService)
    }

    @Test
    fun `getUser should return success when API call succeeds`() = runTest {
        val mockDto = GitHubUserDto(
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
        val mockResponse = Response.success(listOf(mockDto))

        coEvery { apiService.getUsers(any(), any(), any()) } returns mockResponse
        val request = GithubUserRequest(since = null, query = null, perPage = 30)

        // when
        val result = repository.getUsers(request)
        assertThat(result.isSuccess).isEqualTo(true)
        val userPageResult = result.getOrNull()
        assertThat(userPageResult?.users?.size).isEqualTo(1)
        assertThat(userPageResult?.nextSince).isEqualTo(1)
        assertThat(userPageResult?.hasMore).isEqualTo(false)
    }

    @Test
    fun `getUser should return failure when API call fails`() = runTest {
        val mockResponse = Response.error<List<GitHubUserDto>>(
            404,
            "Not Found".toResponseBody("text/plain".toMediaType())
        )

        coEvery { apiService.getUsers(any(), any(), any()) } returns mockResponse
        val request = GithubUserRequest(since = null, query = null, perPage = 30)

        // When
        val result = repository.getUsers(request)

        // Then
        assertThat(result.isFailure).isEqualTo(true)
        assertThat(result.exceptionOrNull()).isEqualTo(NetworkException.NotFoundException())
    }

    @Test
    fun `getUser should calculate hasMore  correctly when the full page returned `() = runTest {
        // Given
        val mockUsers = (1..Constants.USER_PER_PAGE).map { id ->
            GitHubUserDto(
                login = "user$id",
                id = id.toLong(),
                avatarUrl = "https://github.com/images/user$id.gif",
                name = "User $id",
                followers = 10,
                following = 5,
                publicRepos = 1,
                bio = "Bio $id",
                htmlUrl = "https://github.com/user$id"
            )

        }
        val mockResponse = Response.success(mockUsers)
        coEvery { apiService.getUsers() } returns mockResponse

        // when
        val request = GithubUserRequest(since = null, query = null, perPage = 30)
        val result = repository.getUsers(request)

        // then
        assertThat(result.isSuccess).isEqualTo(true)
        assertThat(result.getOrNull()?.hasMore).isEqualTo(true)
    }
}