package org.ivcode.ai.ollama.history

import io.github.ollama4j.models.chat.OllamaChatMessage
import java.util.UUID

interface OllamaHistoryManager {
    val id: UUID
    fun getMessages(): List<OllamaChatMessage>
    fun addMessages(messages: List<OllamaChatMessage>)
}