package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}