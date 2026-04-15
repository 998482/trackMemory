package com.smartmemoryassistant.data.model

data class RuleResult(
    val matched: Boolean,
    val suggestion: String,
    val confidence: Int,       // 0–100
    val ruleType: String       // "LOCATION", "TIME", "HABIT"
)