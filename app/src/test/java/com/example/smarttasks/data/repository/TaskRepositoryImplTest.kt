package com.example.smarttasks.data.repository

import com.example.smarttasks.data.local.TaskDao
import com.example.smarttasks.data.local.model.TaskEntity
import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.remote.model.TaskDto
import com.example.smarttasks.data.remote.model.TaskResponseDto
import com.example.smarttasks.data.util.toDomain
import com.example.smarttasks.data.util.toEntity
import com.example.smarttasks.domain.model.TaskStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryImplTest {
    private lateinit var api: TaskApi
    private lateinit var dao: TaskDao
    private lateinit var repository: TaskRepositoryImpl

    private val sampleEntity = TaskEntity(
        id = "1",
        title = "Sample",
        description = "desc",
        priority = 1,
        targetDate = "2025-09-17",
        dueDate = null,
        status = TaskStatus.UNRESOLVED,
        comment = null
    )
    private val sampleTask = sampleEntity.toDomain()

    @Before
    fun setUp() {
        api = mockk()
        dao = mockk(relaxed = true)
        repository = TaskRepositoryImpl(api, dao)
    }

    @Test
    fun `syncTasks should fetch from api and insert into dao`() = runTest {
        // given
        val dto = TaskDto("1", 1, "Sample", "desc", "2025-09-17", null)
        val response = TaskResponseDto(listOf(dto))
        coEvery { api.getTasks() } returns response

        // when
        repository.syncTasks()

        // then
        coVerify { dao.insertTasks(listOf(dto.toEntity())) }
    }

    @Test
    fun `getTasksByDate should return mapped domain tasks`() = runTest {
        // given
        val expectedList = listOf(sampleEntity, sampleEntity)
        every { dao.getTasksByDate("2025-09-17") } returns flowOf(expectedList)

        // when
        val result = repository.getTasksByDate("2025-09-17").first()

        // then
        assertEquals(listOf(sampleTask, sampleTask), result)
    }

    @Test
    fun `getTaskById should return mapped domain task`() = runTest {
        // given
        every { dao.getTaskById("1") } returns flowOf(sampleEntity)

        // when
        val result = repository.getTaskById("1").first()

        // then
        assertEquals(sampleTask, result)
    }

    @Test
    fun `updateTask should call dao update with entity`() = runTest {
        // when
        repository.updateTask(sampleTask)

        // then
        coVerify { dao.updateTask(sampleEntity) }
    }
}
