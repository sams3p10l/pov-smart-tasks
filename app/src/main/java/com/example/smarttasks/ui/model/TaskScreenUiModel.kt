package com.example.smarttasks.ui.model

data class TaskScreenUiModel(
    val date: String,
    val tasks: List<TaskUi>
) {
    data class TaskUi(
        val id: String,
        val title: String,
        val description: String,
        val dueDate: String,
        val daysLeft: String,
        val statusIconDrawableRes: Int?
    )
}