package com.smartmemoryassistant.data.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartmemoryassistant.data.local.entity.MemoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(item: MemoryItem): Long

    @Update
    suspend fun updateMemory(item: MemoryItem)

    @Delete
    suspend fun deleteMemory(item: MemoryItem)

    @Query("SELECT * FROM memory_items ORDER BY createdAt DESC")
    fun getAllMemories(): Flow<List<MemoryItem>>

    // Search feature ke liye
    @Query("SELECT * FROM memory_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchMemories(query: String): Flow<List<MemoryItem>>

    @Query("SELECT * FROM memory_items WHERE id = :id")
    suspend fun getMemoryById(id: Int): MemoryItem?

    @Query("SELECT * FROM memory_items WHERE category = :category")
    fun getByCategory(category: String): Flow<List<MemoryItem>>
}