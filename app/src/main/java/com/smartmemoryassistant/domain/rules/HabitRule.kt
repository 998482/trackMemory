package com.smartmemoryassistant.domain.rules


import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult
import java.util.Calendar

class HabitRule {

    // Rule: If item saved at same location on same weekday 2+ times → habit detected
    fun evaluate(query: String, memories: List<MemoryItem>): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        val queryLower = query.lowercase().trim()
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        val matchingMemories = memories.filter {
            it.title.lowercase().contains(queryLower)
        }

        if (matchingMemories.isEmpty()) return results

        // Same weekday memories
        val sameDayMemories = matchingMemories.filter { it.dayOfWeek == currentDay }

        if (sameDayMemories.size >= 2) {
            val topLocation = sameDayMemories
                .groupBy { it.location }
                .maxByOrNull { it.value.size }

            val dayName = listOf("", "Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday", "Saturday")[currentDay]

            if (topLocation != null) {
                results.add(
                    RuleResult(
                        matched = true,
                        suggestion = "Habit detected! On ${dayName}s you keep ${query} at: ${topLocation.key}",
                        confidence = 85,
                        ruleType = "HABIT"
                    )
                )
            }
        }

        return results
    }
}