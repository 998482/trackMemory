package com.smartmemoryassistant.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smartmemoryassistant.data.dao.MemoryDao
import com.smartmemoryassistant.data.local.entity.MemoryItem

@Database(
    entities = [MemoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class SmartMemoryDatabase : RoomDatabase() {

    abstract fun memoryDao(): MemoryDao

    companion object {
        @Volatile
        private var INSTANCE: SmartMemoryDatabase? = null

        fun getDatabase(context: Context): SmartMemoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartMemoryDatabase::class.java,
                    "smart_memory_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}