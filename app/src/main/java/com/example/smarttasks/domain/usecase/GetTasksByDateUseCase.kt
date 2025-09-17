package com.example.smarttasks.domain.usecase

import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksByDateUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Task>> {
        return repository.getTasksByDate(date.toString())
    }
}