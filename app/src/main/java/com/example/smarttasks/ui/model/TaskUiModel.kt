package com.example.smarttasks.ui.model

import com.example.smarttasks.domain.model.Task

data class TaskUiModel(
    val title: String,
    val description: String,
)

fun Task.toUiModel() = TaskUiModel(
    title = title,
    description = description.orEmpty(),
)
