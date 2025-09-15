package com.example.smarttasks.data.remote

import com.example.smarttasks.data.remote.model.TaskResponseDto
import retrofit2.http.GET

interface TaskApi {
    @GET("/")
    suspend fun getTasks(): TaskResponseDto
}