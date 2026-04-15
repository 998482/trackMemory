package com.smartmemoryassistant.domain.rules


import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult

class LocationRule {

    // Rule: If an item was saved at the same location 2+ times → high confidence
    fun evaluate(query: String, memories: List<MemoryItem>): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        val queryLower = query.lowercase().trim()

        // Find memories matching the query item name
        val matchingMemories = memories.filter {
            it.title.lowercase().contains(queryLower) ||
                    queryLower.contains(it.title.lowercase())
        }

        if (matchingMemories.isEmpty()) return results

        // Group by location and count
        val locationFrequency = matchingMemories
            .groupBy { it.location.lowercase().trim() }
            .mapValues { it.value.size }

        // Most frequent location
        val topLocation = locationFrequency.maxByOrNull { it.value }

        if (topLocation != null) {
            val total = matchingMemories.size
            val freq = topLocation.value
            val confidence = when {
                freq >= 5 -> 95
                freq >= 3 -> 80
                freq >= 2 -> 65
                else      -> 40
            }

            results.add(
                RuleResult(
                    matched = true,
                    suggestion = "Your ${query} is likely at: ${topLocation.key.replaceFirstChar { it.uppercase() }}",
                    confidence = confidence,
                    ruleType = "LOCATION"
                )
            )
        }

        return results
    }
}