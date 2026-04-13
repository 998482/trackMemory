package com.smartmemoryassistant.data.repository

import com.smartmemoryassistant.data.dao.MemoryDao
import com.smartmemoryassistant.data.local.entity.MemoryItem
import kotlinx.coroutines.flow.Flow

class MemoryRepository(private val dao: MemoryDao) {

    val allMemories: Flow<List<MemoryItem>> = dao.getAllMemories()

    suspend fun addMemory(item: MemoryItem) = dao.insertMemory(item)

    suspend fun updateMemory(item: MemoryItem) = dao.updateMemory(item)

    suspend fun deleteMemory(item: MemoryItem) = dao.deleteMemory(item)

    fun search(query: String): Flow<List<MemoryItem>> = dao.searchMemories(query)

    fun getByCategory(cat: String): Flow<List<MemoryItem>> = dao.getByCategory(cat)
}