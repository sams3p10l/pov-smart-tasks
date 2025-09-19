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
class GetTasksByDateUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTasksByDateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetTasksByDateUseCase(repository)
    }

    @Test
    fun `invoke should return tasks for given date`() = runTest {
        // given
        val date = LocalDate.of(2025, 9, 17)
        val tasks = listOf(
            Task(
                id = "1",
                title = "Task 1",
                description = null,
                priority = 1,
                targetDate = date,
                dueDate = null,
                status = TaskStatus.UNRESOLVED,
                comment = null
            ),
            Task(
                id = "2",
                title = "Task 2",
                description = "desc",
                priority = 2,
                targetDate = date,
                dueDate = null,
                status = TaskStatus.RESOLVED,
                comment = "done"
            )
        )
        every { repository.getTasksByDate(date.toString()) } returns flowOf(tasks)

        // when
        val result = useCase(date)

        // then
        assertEquals(tasks, result.first())
    }

    @Test
    fun `invoke should return empty list if no tasks exist`() = runTest {
        // given
        val date = LocalDate.of(2025, 9, 17)
        every { repository.getTasksByDate(date.toString()) } returns flowOf(emptyList())

        // when
        val result = useCase(date)

        // then
        assertEquals(emptyList<Task>(), result.first())
    }
}
