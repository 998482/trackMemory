package com.smartmemoryassistant.data.repository



import com.smartmemoryassistant.data.model.MemoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MemoryRepository {

    // In-memory store (replace with Room DB for persistence)
    private val _memories = MutableStateFlow<List<MemoryItem>>(
        listOf(
            MemoryItem("Keys",    "Dining table tray",         "Saved 08:45 AM",  "Most searched"),
            MemoryItem("Wallet",  "Laptop bag front pocket",   "Saved yesterday", "Habit match"),
            MemoryItem("Charger", "Study desk drawer",         "Saved 2 days ago","Photo attached"),
            // Duplicate entries to let rules fire
            MemoryItem("Keys",    "Dining table tray",         "Saved yesterday", "Habit match",  hourOfDay = 8),
            MemoryItem("Keys",    "Dining table tray",         "Saved 2 days ago","Location",     hourOfDay = 9),
            MemoryItem("Wallet",  "Laptop bag front pocket",   "Saved Monday",    "Habit match",  hourOfDay = 8, dayOfWeek = 2),
        )
    )

    val memories: StateFlow<List<MemoryItem>> = _memories

    fun addMemory(item: MemoryItem) {
        _memories.value = _memories.value + item
    }

    fun getAllMemories(): List<MemoryItem> = _memories.value
}
