package com.example.smarttasks.ui.model

import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.ui.extension.daysUntil
import com.example.smarttasks.ui.extension.prettify
import java.time.LocalDate

data class TaskUiModel(
    val title: String,
    val description: String,
    val dueDate: String,
    val daysLeft: String
)

fun Task.toUiModel() = TaskUiModel(
    title = title,
    description = description.orEmpty(),
    dueDate = dueDate?.prettify() ?: "",
    daysLeft = dueDate?.let { LocalDate.now().daysUntil(it) }.toString()
)