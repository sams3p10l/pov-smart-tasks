package com.example.smarttasks.domain.repository

import com.example.smarttasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksByDate(date: String): Flow<List<Task>>

    fun getTaskById(id: String): Flow<Task>

    suspend fun updateTask(updatedTask: Task)

    suspend fun syncTasks()
}