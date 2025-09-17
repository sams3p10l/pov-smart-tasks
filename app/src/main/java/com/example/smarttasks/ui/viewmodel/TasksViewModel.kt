package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.usecase.GetTasksUseCase
import com.example.smarttasks.ui.extension.prettify
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskScreenUiModel("", emptyList()))
    val uiState: StateFlow<TaskScreenUiModel> = _uiState

    private val currentDate = LocalDate.now()
    private val targetDate = MutableStateFlow<LocalDate>(currentDate)

    init {
        combine(getTasksUseCase(), targetDate) { tasks, date ->
            val filtered = tasks
                .filter { it.targetDate == date }
                .map { it.toUiModel() }

            TaskScreenUiModel(
                date = formatDate(date),
                tasks = filtered
            )
        }
        .onEach {
            _uiState.value = it
        }
        .launchIn(viewModelScope)
    }

    fun incrementDate() {
        targetDate.value = targetDate.value.plusDays(1)
    }

    fun decrementDate() {
        targetDate.value = targetDate.value.minusDays(1)
    }

    private fun formatDate(targetDate: LocalDate): String {
        return when (targetDate) {
            currentDate -> "Today"
            currentDate.minusDays(1) -> "Yesterday"
            currentDate.plusDays(1) -> "Tomorrow"
            else -> targetDate.prettify()
        }
    }
}