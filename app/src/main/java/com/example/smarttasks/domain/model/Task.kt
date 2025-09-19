package com.example.smarttasks.domain.model

import java.time.LocalDate

data class Task(
    val id: String,
    val priority: Int,
    val title: String,
    val description: String? = null,
    val targetDate: LocalDate,
    val dueDate: LocalDate? = null,
    val status: TaskStatus,
    val comment: String? = null
)