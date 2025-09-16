package com.example.smarttasks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.ui.model.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(Screen.Splash)
    val currentScreen: StateFlow<Screen> =
        _currentScreen.stateIn(viewModelScope, SharingStarted.Lazily, Screen.Splash)

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }
}