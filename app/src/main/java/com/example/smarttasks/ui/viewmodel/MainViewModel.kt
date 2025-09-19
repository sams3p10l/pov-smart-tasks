package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.domain.usecase.FetchTasksUseCase
import com.example.smarttasks.ui.model.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    fetchTasksUseCase: FetchTasksUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<Screen> = MutableStateFlow(Screen.Splash)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchTasksUseCase()
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.value = screen
    }
}