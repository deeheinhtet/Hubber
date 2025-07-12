package com.dee.hubber.user.domain.usecase

import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat

@RunWith(MockitoJUnitRunner::class)
class GetUsersUseCaseTest {

    @MockK
    private lateinit var repository: UserRepository
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetUsersUseCase(repository)
    }

    @Test
    fun `invoke should call getUsers when query parameter is null`() = runTest {
        // Given
        val request = GithubUserRequest(since = null, query = null, perPage = 30)

        val expectedResult = UsersPageResult(
            users = emptyList(),
            nextSince = null,
            hasMore = false,
            currentSince = null
        )


        coEvery { repository.getUsers(request) } returns Result.success(expectedResult)
        // When
        val result = useCase.invoke(request)

        coVerify { repository.getUsers(request) }
        coVerify(exactly = 0) { repository.searchUsers(any()) }

        // Then
        assertThat(result.isSuccess).isEqualTo(true)
        assertThat(result.getOrNull()).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should call searchUsers when query parameter is not null`() = runTest {
        // Given
        val request = GithubUserRequest(since = null, query = "user", perPage = 30)

        val expectedResult = UsersPageResult(
            users = emptyList(),
            nextSince = null,
            hasMore = false,
            currentSince = null
        )


        coEvery { repository.searchUsers(request) } returns Result.success(expectedResult)
        // When
        val result = useCase.invoke(request)

        coVerify { repository.searchUsers(request) }
        coVerify(exactly = 0) { repository.getUsers(any()) }

        // Then
        assertThat(result.isSuccess).isEqualTo(true)
        assertThat(result.getOrNull()).isEqualTo(expectedResult)
    }
}