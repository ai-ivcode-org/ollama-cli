package org.ivcode.ai.synapp.history

import java.util.UUID

interface OllamaHistoryManager {
    val id: UUID
    fun getMessages(): List<OllamaHistoryMessage>
    fun addMessages(messages: List<OllamaHistoryMessage>)
}