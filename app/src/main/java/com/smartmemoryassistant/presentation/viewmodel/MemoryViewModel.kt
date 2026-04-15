package com.smartmemoryassistant.presentation.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult
import com.smartmemoryassistant.data.repository.MemoryRepository
import com.smartmemoryassistant.domain.rules.RuleEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemoryViewModel : ViewModel() {

    private val repository = MemoryRepository()
    private val ruleEngine = RuleEngine()

    val memories: StateFlow<List<MemoryItem>> = repository.memories

    private val _ruleResult = MutableStateFlow<RuleResult?>(null)
    val ruleResult: StateFlow<RuleResult?> = _ruleResult

    private val _voiceText = MutableStateFlow("")
    val voiceText: StateFlow<String> = _voiceText

    // Called when user searches or asks voice query
    fun onQuery(query: String) {
        viewModelScope.launch {
            val result = ruleEngine.getBestSuggestion(
                query = query,
                allMemories = repository.getAllMemories()
            )
            _ruleResult.value = result
        }
    }

    // Called after voice recognition returns text
    fun onVoiceResult(text: String) {
        _voiceText.value = text
        onQuery(text)
    }

    // Called after camera captures image (location typed manually for now)
    fun saveMemoryWithPhoto(title: String, location: String, photoUri: String) {
        val item = MemoryItem(
            title = title,
            location = location,
            savedAt = "Just now",
            accentLabel = "Photo attached",
            photoUri = photoUri
        )
        repository.addMemory(item)
    }

    fun saveMemory(title: String, location: String) {
        val item = MemoryItem(
            title = title,
            location = location,
            savedAt = "Just now",
            accentLabel = "Saved"
        )
        repository.addMemory(item)
    }
}