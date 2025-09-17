package com.example.smarttasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smarttasks.data.local.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}