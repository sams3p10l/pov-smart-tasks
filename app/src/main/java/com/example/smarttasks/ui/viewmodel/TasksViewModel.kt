package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.usecase.GetTasksByDateUseCase
import com.example.smarttasks.ui.extension.prettify
import com.example.smarttasks.ui.extension.toUiModel
import com.example.smarttasks.ui.model.TaskScreenUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksByDateUseCase: GetTasksByDateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskScreenUiModel("", emptyList()))
    val uiState: StateFlow<TaskScreenUiModel> = _uiState

    private val currentDate = LocalDate.now()
    private val targetDate = MutableStateFlow<LocalDate>(currentDate)

    init {
        buildUiState()
    }

    fun incrementDate() {
        targetDate.value = targetDate.value.plusDays(1)
    }

    fun decrementDate() {
        targetDate.value = targetDate.value.minusDays(1)
    }

    private fun buildUiState() {
        targetDate
            .flatMapLatest { date -> //getting latest selected date
                getTasksByDateUseCase(date).map { tasks -> //fetching tasks for that date
                    date to tasks //pairing result to current date because we need the date below
                }
            }.map { (date, tasks) ->
                val mapped = tasks
                    .sortedWith(
                        compareByDescending<Task> {
                            it.priority //sort by priority, biggest first
                        }.thenBy {
                            it.dueDate //if priority is the same, task with closer due date comes first
                        }
                    )
                    .map {
                        it.toUiModel() //map domain tasks to ui data
                    }

                TaskScreenUiModel(
                    date = formatDate(date),
                    tasks = mapped
                )
            }.onEach {
                _uiState.value = it //post the value
            }
            .launchIn(viewModelScope)
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