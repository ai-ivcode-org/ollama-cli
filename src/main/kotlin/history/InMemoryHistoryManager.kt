package org.ivcode.ai.synapp.history

import java.util.*

class InMemoryHistoryManager (
    override val id: UUID = UUID.randomUUID(),
) : OllamaHistoryManager {
    private val history = mutableListOf<OllamaHistoryMessage>()

    override fun getMessages(): List<OllamaHistoryMessage> {
        return history.toList()
    }

    override fun addMessages(messages: List<OllamaHistoryMessage>) {
        history.addAll(messages)
    }
}