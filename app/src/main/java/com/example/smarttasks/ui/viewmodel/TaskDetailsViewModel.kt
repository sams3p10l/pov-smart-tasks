package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.usecase.GetTaskByIdUseCase
import com.example.smarttasks.domain.usecase.UpdateTaskUseCase
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.model.UiState
import com.example.smarttasks.ui.util.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
): ViewModel() {
    private val taskId = MutableStateFlow<String?>(null)

    val uiState: StateFlow<UiState<TaskDetailsUiModel>> =
        taskId
            .filterNotNull()
            .flatMapLatest { id ->
                getTaskByIdUseCase(id)
            }
            .map { task ->
                if (task == null) {
                    UiState.Error("Task not found")
                } else {
                    UiState.Success(task.toDetailsUiModel())
                }
            }
            .catch { emit(UiState.Error(it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading
            )

    fun updateTask(id: String, status: TaskStatus, comment: String? = null) {
        viewModelScope.launch {
            updateTaskUseCase(id, status, comment)
        }
    }

    fun setTaskId(id: String) {
        taskId.value = id
    }
}