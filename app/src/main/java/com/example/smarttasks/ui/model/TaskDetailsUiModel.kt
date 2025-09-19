package com.example.smarttasks.ui.model

import com.example.smarttasks.domain.model.TaskStatus

data class TaskDetailsUiModel(
    val title: String,
    val dueDate: String,
    val daysLeft: String,
    val description: String,
    val status: TaskStatus,
    val comment: String
)
