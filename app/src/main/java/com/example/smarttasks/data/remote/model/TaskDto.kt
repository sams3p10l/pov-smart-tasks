package com.example.smarttasks.data.remote.model

import com.example.smarttasks.domain.model.Task
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@Serializable
data class TaskResponseDto(
    private val tasks: List<TaskDto>
) {
    fun get() = tasks
}

/**
 * Data class for serialization of task Json object into data model.
 * For now, assuming that description and due date are optional fields.
 */
@Serializable
data class TaskDto(
    val id: String,
    @SerialName("Priority") val priority: Int = 0,
    @SerialName("Title") val title: String,
    @SerialName("Description") val description: String? = null,
    @SerialName("TargetDate") val targetDate: String,
    @SerialName("DueDate") val dueDate: String? = null,
)

@OptIn(ExperimentalTime::class)
fun TaskDto.toDomain() = Task(
    id = id,
    priority = priority,
    title = title,
    description = description,
    targetDate = LocalDate.parse(targetDate),
    dueDate = dueDate?.let { LocalDate.parse(it) },
)
