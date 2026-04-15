package com.smartmemoryassistant.domain.rules



import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult

class RuleEngine {

    private val locationRule = LocationRule()
    private val timeRule = TimeRule()
    private val habitRule = HabitRule()

    fun evaluate(
        query: String,
        allMemories: List<MemoryItem>
    ): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        results.addAll(locationRule.evaluate(query, allMemories))
        results.addAll(timeRule.evaluate(query, allMemories))
        results.addAll(habitRule.evaluate(query, allMemories))

        return results
            .filter { it.matched }
            .sortedByDescending { it.confidence }
    }

    fun getBestSuggestion(
        query: String,
        allMemories: List<MemoryItem>
    ): RuleResult? {
        return evaluate(query, allMemories).firstOrNull()
    }
}