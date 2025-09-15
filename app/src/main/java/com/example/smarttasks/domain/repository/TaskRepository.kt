package com.example.smarttasks.domain.repository

import com.example.smarttasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
}