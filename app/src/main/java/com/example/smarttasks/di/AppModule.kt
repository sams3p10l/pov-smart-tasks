package com.example.smarttasks.di

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
            .baseUrl(BASE_API_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}

private const val RETROFIT_CONTENT_TYPE = "application/json"
private const val BASE_API_URL = "http://demo9877360.mockable.io/"