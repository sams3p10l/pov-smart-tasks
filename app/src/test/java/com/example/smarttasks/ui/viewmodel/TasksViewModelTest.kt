package com.example.smarttasks.ui.viewmodel

import app.cash.turbine.test
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.usecase.GetTasksByDateUseCase
import com.example.smarttasks.ui.model.UiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {
    private lateinit var getTasksByDateUseCase: GetTasksByDateUseCase
    private lateinit var viewModel: TasksViewModel

    private val sampleTask = Task(
        id = "1",
        title = "Task",
        description = null,
        priority = 1,
        targetDate = LocalDate.now(),
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )
    private val sampleTaskTomorrow = Task(
        id = "1",
        title = "Task",
        description = null,
        priority = 1,
        targetDate = LocalDate.now().plusDays(1),
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )
    private val sampleTaskYesterday = Task(
        id = "1",
        title = "Task",
        description = null,
        priority = 1,
        targetDate = LocalDate.now().minusDays(1),
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )

    @Before
    fun setUp() {
        getTasksByDateUseCase = mockk()
    }

    @Test
    fun `uiState emits Loading then Success when tasks are loaded`() = runTest {
        val today = LocalDate.now()
        every { getTasksByDateUseCase(today) } returns flowOf(listOf(sampleTask))

        viewModel = TasksViewModel(getTasksByDateUseCase)

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())

            val success = awaitItem()
            assertTrue(success is UiState.Success)

            val data = (success as UiState.Success).data
            assertEquals("Today", data.date)
            assertEquals(1, data.tasks.size)
            assertEquals("Task", data.tasks.first().title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `incrementDate emits Success for Tomorrow after increment`() = runTest {
        val tomorrow = LocalDate.now().plusDays(1)
        every { getTasksByDateUseCase(LocalDate.now()) } returns flowOf(listOf(sampleTask))
        every { getTasksByDateUseCase(tomorrow) } returns flowOf(listOf(sampleTaskTomorrow))

        viewModel = TasksViewModel(getTasksByDateUseCase)

        viewModel.uiState.test {
            skipItems(2) //skip loading and today emissions

            // trigger
            viewModel.incrementDate()

            val success = awaitItem()
            assertTrue(success is UiState.Success)
            val data = (success as UiState.Success).data
            assertEquals("Tomorrow", data.date)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `decrementDate emits Success for Yesterday after decrement`() = runTest {
        val yesterday = LocalDate.now().minusDays(1)
        every { getTasksByDateUseCase(LocalDate.now()) } returns flowOf(listOf(sampleTask))
        every { getTasksByDateUseCase(yesterday) } returns flowOf(listOf(sampleTaskYesterday))

        viewModel = TasksViewModel(getTasksByDateUseCase)

        viewModel.uiState.test {
            skipItems(2) //skip loading and today emissions

            // trigger
            viewModel.decrementDate()

            val success = awaitItem()
            assertTrue(success is UiState.Success)
            val data = (success as UiState.Success).data
            assertEquals("Yesterday", data.date)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState emits Error when exception from useCase`() = runTest {
        every { getTasksByDateUseCase(any()) } returns flow { throw RuntimeException("Boom") }

        viewModel = TasksViewModel(getTasksByDateUseCase)

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())

            val error = awaitItem()
            assertTrue(error is UiState.Error)
            assertEquals("Boom", (error as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
