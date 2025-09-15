package com.example.smarttasks.data.repository

import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.remote.model.toDomain
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return flow {
            val mapped = api.getTasks().get().map { it.toDomain() }
            emit(mapped)
        }
    }
}