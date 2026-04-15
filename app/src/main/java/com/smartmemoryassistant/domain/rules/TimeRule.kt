package com.smartmemoryassistant.domain.rules



import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult
import java.util.Calendar

class TimeRule {

    // Rule: If item is usually saved in morning hours → suggest morning location
    fun evaluate(query: String, memories: List<MemoryItem>): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        val queryLower = query.lowercase().trim()
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val matchingMemories = memories.filter {
            it.title.lowercase().contains(queryLower)
        }

        if (matchingMemories.isEmpty()) return results

        // Define time buckets
        val morningMemories  = matchingMemories.filter { it.hourOfDay in 5..11 }
        val afternoonMemories= matchingMemories.filter { it.hourOfDay in 12..17 }
        val eveningMemories  = matchingMemories.filter { it.hourOfDay in 18..23 }

        val currentBucketMemories = when (currentHour) {
            in 5..11  -> morningMemories
            in 12..17 -> afternoonMemories
            else      -> eveningMemories
        }

        if (currentBucketMemories.isNotEmpty()) {
            val topLocation = currentBucketMemories
                .groupBy { it.location }
                .maxByOrNull { it.value.size }

            val timeLabel = when (currentHour) {
                in 5..11  -> "morning"
                in 12..17 -> "afternoon"
                else       -> "evening"
            }

            if (topLocation != null) {
                results.add(
                    RuleResult(
                        matched = true,
                        suggestion = "In the $timeLabel, you usually keep ${query} at: ${topLocation.key}",
                        confidence = 70,
                        ruleType = "TIME"
                    )
                )
            }
        }

        return results
    }
}