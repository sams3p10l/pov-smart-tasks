package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.usecase.GetTasksByDateUseCase
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.util.prettify
import com.example.smarttasks.ui.util.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksByDateUseCase: GetTasksByDateUseCase,
) : ViewModel() {
    private val currentDate = LocalDate.now()
    private val targetDate = MutableStateFlow<LocalDate>(currentDate)

    val uiState: StateFlow<TaskScreenUiModel> =
        targetDate
            .flatMapLatest { date ->
                getTasksByDateUseCase(date).map { tasks -> date to tasks }
            }
            .map { (date, tasks) ->
                val mapped = tasks
                    .sortedWith(compareByDescending<Task> {
                        it.priority
                    }.thenBy {
                        it.dueDate
                    })
                    .map { it.toUiModel() }

                TaskScreenUiModel(
                    date = formatDate(date),
                    tasks = mapped
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = TaskScreenUiModel("", emptyList())
            )

    fun incrementDate() {
        targetDate.update { it.plusDays(1) }
    }

    fun decrementDate() {
        targetDate.update { it.minusDays(1) }
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