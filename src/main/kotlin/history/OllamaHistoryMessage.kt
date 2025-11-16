package org.ivcode.ai.synapp.history

data class OllamaHistoryMessage (
    val role: String,
    val content: String?,
    val toolCalls: List<ToolCall>?,
)