package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String, newStatus: TaskStatus, comment: String? = null) {
        val task = repository.getTaskById(id).firstOrNull()
        if (task != null) {
            repository.updateTask(
                task.copy(status = newStatus, comment = comment ?: task.comment)
            )
        }
    }
}