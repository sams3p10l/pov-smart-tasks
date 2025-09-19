package com.example.smarttasks.ui.viewmodel

import com.example.smarttasks.domain.usecase.FetchTasksUseCase
import com.example.smarttasks.ui.model.Screen
import com.example.smarttasks.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fetchTasksUseCase: FetchTasksUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        fetchTasksUseCase = mockk()
    }

    @Test
    fun `init calls fetchTasksUseCase`() = runTest {
        coEvery { fetchTasksUseCase() } just Runs

        viewModel = MainViewModel(fetchTasksUseCase)

        advanceUntilIdle()

        coVerify { fetchTasksUseCase() }
    }

    @Test
    fun `navigateTo updates uiState`() = runTest {
        coEvery { fetchTasksUseCase() } just Runs
        viewModel = MainViewModel(fetchTasksUseCase)

        viewModel.navigateTo(Screen.Tasks)

        assertEquals(Screen.Tasks, viewModel.uiState.value)
    }
}