package com.example.smarttasks.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.model.TaskStatus
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@Entity("tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priority: Int,
    val targetDate: String,
    val dueDate: String?,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String? = null
)

@OptIn(ExperimentalTime::class)
fun TaskEntity.toDomain() = Task(
    id = id,
    priority = priority,
    title = title,
    description = description,
    targetDate = LocalDate.parse(targetDate),
    dueDate = dueDate?.let { LocalDate.parse(it) },
    //todo status, comment
)
