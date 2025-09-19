package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: UpdateTaskUseCase

    private val sampleTask = Task(
        id = "1",
        title = "Sample Task",
        description = "desc",
        priority = 1,
        targetDate = LocalDate.now(),
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = UpdateTaskUseCase(repository)
    }

    @Test
    fun `invoke should update task with new status`() = runTest {
        // given
        every { repository.getTaskById("1") } returns flowOf(sampleTask)

        // when
        useCase("1", TaskStatus.RESOLVED)

        // then
        coVerify {
            repository.updateTask(sampleTask.copy(status = TaskStatus.RESOLVED, comment = null))
        }
    }

    @Test
    fun `invoke should update task with new status and comment`() = runTest {
        // given
        every { repository.getTaskById("1") } returns flowOf(sampleTask)

        // when
        useCase("1", TaskStatus.CANT_RESOLVE, "Could not complete")

        // then
        coVerify {
            repository.updateTask(sampleTask.copy(status = TaskStatus.CANT_RESOLVE, comment = "Could not complete"))
        }
    }

    @Test
    fun `invoke should not update when task not found`() = runTest {
        // given
        every { repository.getTaskById("404") } returns emptyFlow()

        // when
        useCase("404", TaskStatus.RESOLVED)

        // then
        coVerify(exactly = 0) { repository.updateTask(any()) }
    }
}
