package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.usecase.GetTasksUseCase
import com.example.smarttasks.ui.model.TaskUiModel
import com.example.smarttasks.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskUiModel>>(emptyList())
    val tasks: StateFlow<List<TaskUiModel>> =
        _tasks.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        getTasksUseCase()
            .map { domainList -> domainList.map { it.toUiModel() } }
            .onEach { _tasks.value = it }
            .launchIn(viewModelScope)
    }
}