package com.example.smarttasks.ui.extension

import androidx.compose.ui.graphics.Color
import com.example.smarttasks.R
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.theme.Green
import com.example.smarttasks.ui.theme.Red
import com.example.smarttasks.ui.theme.Yellow
import java.time.LocalDate

fun Task.toUiModel() = TaskScreenUiModel.TaskUi(
    id = id,
    title = title,
    description = description.orEmpty(),
    dueDate = dueDate?.prettify() ?: "",
    daysLeft = mapDaysLeft(dueDate),
    statusIconDrawableRes = mapStatusIconDrawableRes(status)
)

private fun mapStatusIconDrawableRes(status: TaskStatus): Int? {
    return when (status) {
        TaskStatus.UNRESOLVED -> null
        TaskStatus.CANT_RESOLVE -> R.drawable.btn_unresolved
        TaskStatus.RESOLVED -> R.drawable.btn_resolved
    }
}

fun Task.toDetailsUiModel() = TaskDetailsUiModel(
    title = title,
    description = description.orEmpty(),
    dueDate = dueDate?.prettify() ?: "",
    daysLeft = mapDaysLeft(dueDate),
    accentColor = mapAccentColor(status),
    status = status,
    statusText = mapStatus(status),
    statusTextColor = mapStatusTextColor(status),
    comment = comment.orEmpty()
)

private fun mapDaysLeft(dueDate: LocalDate?): String {
    return dueDate?.let { LocalDate.now().daysUntil(it) }.toString()
}

private fun mapAccentColor(status: TaskStatus): Color {
    return when (status) {
        TaskStatus.CANT_RESOLVE, TaskStatus.UNRESOLVED -> Red
        TaskStatus.RESOLVED -> Green
    }
}

private fun mapStatus(status: TaskStatus): String {
    return when (status) {
        TaskStatus.CANT_RESOLVE, TaskStatus.UNRESOLVED -> "Unresolved"
        TaskStatus.RESOLVED -> "Resolved"
    }
}

private fun mapStatusTextColor(status: TaskStatus): Color {
    return when (status) {
        TaskStatus.CANT_RESOLVE -> Red
        TaskStatus.UNRESOLVED -> Yellow
        TaskStatus.RESOLVED -> Green
    }
}