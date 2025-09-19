package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class GetTaskByIdUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskByIdUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke should return task from repository`() = runTest {
        // given
        val task = Task(
            id = "123",
            title = "Test task",
            description = "Some description",
            priority = 1,
            targetDate = LocalDate.now(),
            dueDate = null,
            status = TaskStatus.UNRESOLVED,
            comment = null
        )
        every { repository.getTaskById("123") } returns flowOf(task)

        // when
        val result = useCase("123")

        // then
        assertEquals(task, result.first())
    }

    @Test
    fun `invoke should return null if repository returns null`() = runTest {
        // given
        every { repository.getTaskById("999") } returns flowOf(null)

        // when
        val result = useCase("999")

        // then
        assertEquals(null, result.first())
    }
}