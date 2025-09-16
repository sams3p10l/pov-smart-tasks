package com.example.smarttasks.ui.model

sealed class Screen {
    object Splash : Screen()
    object Tasks : Screen()
    object TaskDetails : Screen()
}