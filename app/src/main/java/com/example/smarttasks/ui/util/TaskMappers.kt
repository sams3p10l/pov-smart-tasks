package com.example.smarttasks.ui.util

import com.example.smarttasks.R
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.model.TaskScreenUiModel
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
    status = status,
    comment = comment.orEmpty()
)

private fun mapDaysLeft(dueDate: LocalDate?): String {
    return dueDate?.let { LocalDate.now().daysUntil(it).toString() }.orEmpty()
}