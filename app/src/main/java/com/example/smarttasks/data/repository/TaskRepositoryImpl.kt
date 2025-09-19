package com.example.smarttasks.data.repository

import com.example.smarttasks.data.local.TaskDao
import com.example.smarttasks.data.local.model.TaskEntity
import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.util.toDomain
import com.example.smarttasks.data.util.toEntity
import com.example.smarttasks.domain.model.Task
import com.example.smarttasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun syncTasks() {
        dao.insertTasks(
            api.getTasks().tasks.map { it.toEntity() }
        )
    }

    override fun getTasksByDate(date: String): Flow<List<Task>> {
        return dao.getTasksByDate(date).map { it.map(TaskEntity::toDomain) }
    }

    override fun getTaskById(id: String): Flow<Task?> {
        return dao.getTaskById(id).map { it?.toDomain() }
    }

    override suspend fun updateTask(updatedTask: Task) {
        dao.updateTask(updatedTask.toEntity())
    }
}