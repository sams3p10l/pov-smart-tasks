package com.example.smarttasks.ui.model

sealed class Screen {
    object Splash : Screen()
    object Tasks : Screen()
    data class TaskDetails(val id: String) : Screen()
}