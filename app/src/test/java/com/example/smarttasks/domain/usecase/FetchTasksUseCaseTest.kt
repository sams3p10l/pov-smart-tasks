package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchTasksUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: FetchTasksUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = FetchTasksUseCase(repository)
    }

    @Test
    fun `invoke should call repository syncTasks`() = runTest {
        // when
        useCase()

        // then
        coVerify { repository.syncTasks() }
    }
}