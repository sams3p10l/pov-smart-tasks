package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.repository.TaskRepository
import javax.inject.Inject

class FetchTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.syncTasks()
    }
}