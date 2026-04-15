package com.smartmemoryassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smartmemoryassistant.data.local.database.SmartMemoryDatabase
import com.smartmemoryassistant.data.local.entity.MemoryItem
import com.smartmemoryassistant.data.repository.MemoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MemoryRepository

    // Step 1: repository pehle init karo
    init {
        val dao = SmartMemoryDatabase.getDatabase(application).memoryDao()
        repository = MemoryRepository(dao)
    }

    // Step 2: stateIn ko property declaration mein use karo — init ke baad
    val allMemories: StateFlow<List<MemoryItem>> = repository.allMemories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addMemory(item: MemoryItem) = viewModelScope.launch {
        repository.addMemory(item)
    }

    fun search(query: String) = repository.search(query)
}