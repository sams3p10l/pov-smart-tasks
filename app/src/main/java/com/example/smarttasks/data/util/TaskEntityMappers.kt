package com.example.smarttasks.data.util

import com.example.smarttasks.data.local.model.TaskEntity
import com.example.smarttasks.data.remote.model.TaskDto
import com.example.smarttasks.domain.model.Task
import java.time.LocalDate

fun TaskEntity.toDomain() = Task(
    id = id,
    priority = priority,
    title = title,
    description = description,
    targetDate = targetDate.toLocalDateOrNull() ?: LocalDate.now(),
    dueDate = dueDate?.toLocalDateOrNull(),
    status = status,
    comment = comment
)

fun Task.toEntity() = TaskEntity(
    id = id,
    priority = priority,
    title = title,
    description = description,
    targetDate = targetDate.toString(),
    dueDate = dueDate?.toString(),
    status = status,
    comment = comment
)

fun TaskDto.toEntity() = TaskEntity(
    id = id,
    priority = priority,
    title = title,
    description = description,
    targetDate = targetDate,
    dueDate = dueDate
)

private fun String.toLocalDateOrNull(): LocalDate? =
    runCatching { LocalDate.parse(this) }.getOrNull()