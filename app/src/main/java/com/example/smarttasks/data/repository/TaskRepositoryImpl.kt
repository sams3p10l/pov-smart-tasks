package com.example.smarttasks.data.repository

import com.example.smarttasks.data.local.TaskDao
import com.example.smarttasks.data.local.model.toDomain
import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.remote.model.toEntity
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
            api.getTasks().get().map { it.toEntity() }
        )
    }

    override fun getTasksByDate(date: String): Flow<List<Task>> {
        return dao.getTasksByDate(date).map { entities -> entities.map { it.toDomain() } }
    }
}