package com.smartmemoryassistant.data.model

import java.util.Calendar

data class MemoryItem(
    val title: String,
    val location: String,
    val savedAt: String,
    val accentLabel: String,
    val photoUri: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val dayOfWeek: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
    val hourOfDay: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    val id: Long = 0L
)