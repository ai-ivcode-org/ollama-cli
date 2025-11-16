package org.ivcode.ai.ollama.history

import io.github.ollama4j.models.chat.OllamaChatMessage
import java.util.*

class InMemoryHistoryManager (
    override val id: UUID = UUID.randomUUID(),
) : OllamaHistoryManager {
    private val history = mutableListOf<OllamaChatMessage>()

    override fun getMessages(): List<OllamaChatMessage> {
        return history.toList()
    }

    override fun addMessages(messages: List<OllamaChatMessage>) {
        history.addAll(messages)
    }
}