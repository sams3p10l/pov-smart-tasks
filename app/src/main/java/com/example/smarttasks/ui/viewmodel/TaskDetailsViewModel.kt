package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.usecase.GetTaskByIdUseCase
import com.example.smarttasks.domain.usecase.UpdateTaskUseCase
import com.example.smarttasks.ui.extension.toDetailsUiModel
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<TaskDetailsUiModel?>(null)
    val uiState: StateFlow<TaskDetailsUiModel?> = _uiState

    private val taskId = MutableStateFlow<String?>(null)

    init {
        taskId.filterNotNull()
            .flatMapLatest {
                getTaskByIdUseCase(it)
            }
            .map {
                it.toDetailsUiModel()
            }
            .onEach {
                _uiState.value = it
            }
            .launchIn(viewModelScope)
    }

    fun updateTask(id: String, status: TaskStatus, comment: String? = null) {
        viewModelScope.launch {
            updateTaskUseCase(id, status, comment)
        }
    }

    fun setTaskId(id: String) {
        taskId.value = id
    }
}