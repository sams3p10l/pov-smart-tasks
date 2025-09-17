package com.example.smarttasks.di

import com.example.smarttasks.data.local.TaskDao
import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.repository.TaskRepositoryImpl
import com.example.smarttasks.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi,
        dao: TaskDao
    ): TaskRepository {
        return TaskRepositoryImpl(api, dao)
    }
}