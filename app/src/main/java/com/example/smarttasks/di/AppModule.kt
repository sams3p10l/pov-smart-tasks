package com.example.smarttasks.di

import com.example.smarttasks.data.remote.TaskApi
import com.example.smarttasks.data.repository.TaskRepositoryImpl
import com.example.smarttasks.domain.repository.TaskRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val json = Json {
        prettyPrint = false
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = RETROFIT_CONTENT_TYPE.toMediaType()
        return Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi
    ): TaskRepository {
        return TaskRepositoryImpl(api)
    }
}

private const val RETROFIT_CONTENT_TYPE = "application/json"
private const val RETROFIT_BASE_URL = "http://demo9877360.mockable.io"