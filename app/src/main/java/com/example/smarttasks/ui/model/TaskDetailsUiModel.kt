package com.example.smarttasks.ui.model

import androidx.compose.ui.graphics.Color
import com.example.smarttasks.domain.model.TaskStatus

data class TaskDetailsUiModel(
    val title: String,
    val dueDate: String,
    val daysLeft: String,
    val description: String,
    val accentColor: Color,
    val status: TaskStatus,
    val statusText: String,
    val statusTextColor: Color,
    val comment: String
)
