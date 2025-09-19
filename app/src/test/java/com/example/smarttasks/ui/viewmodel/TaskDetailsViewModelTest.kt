package com.example.smarttasks.ui.viewmodel

import app.cash.turbine.test
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.usecase.GetTaskByIdUseCase
import com.example.smarttasks.domain.usecase.UpdateTaskUseCase
import com.example.smarttasks.ui.model.UiState
import com.example.smarttasks.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    private lateinit var updateTaskUseCase: UpdateTaskUseCase
    private lateinit var viewModel: TaskDetailsViewModel

    private val sampleTask = Task(
        id = "1",
        title = "Test task",
        description = "desc",
        priority = 1,
        targetDate = LocalDate.now(),
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )

    @Before
    fun setup() {
        getTaskByIdUseCase = mockk()
        updateTaskUseCase = mockk(relaxed = true)

        viewModel = TaskDetailsViewModel(getTaskByIdUseCase, updateTaskUseCase)
    }

    @Test
    fun `when task exists, uiState emits Success`() = runTest {
        every { getTaskByIdUseCase("1") } returns flowOf(sampleTask)

        viewModel.setTaskId("1")

        viewModel.uiState.test {
            skipItems(1) // Loading
            val state = awaitItem()
            assertTrue(state is UiState.Success)
            assertEquals("Test task", (state as UiState.Success).data.title)
        }
    }

    @Test
    fun `when task not found, uiState emits Error`() = runTest {
        every { getTaskByIdUseCase("404") } returns flowOf(null)

        viewModel.setTaskId("404")

        viewModel.uiState.test {
            skipItems(1) // Loading
            val state = awaitItem()
            assertTrue(state is UiState.Error)
        }
    }

    @Test
    fun `updateTask calls useCase`() = runTest {
        coEvery { updateTaskUseCase(any(), any(), any()) } just Runs

        viewModel.updateTask("1", TaskStatus.RESOLVED, "done")

        advanceUntilIdle()
        coVerify { updateTaskUseCase("1", TaskStatus.RESOLVED, "done") }
    }
}
