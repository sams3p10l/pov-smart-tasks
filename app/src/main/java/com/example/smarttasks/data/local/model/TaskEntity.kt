package com.example.smarttasks.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smarttasks.domain.model.TaskStatus

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val priority: Int,
    val targetDate: String,
    val dueDate: String?,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String? = null
)
