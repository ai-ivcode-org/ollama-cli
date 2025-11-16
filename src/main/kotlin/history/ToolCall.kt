package org.ivcode.ai.synapp.history

data class ToolCall (
    val name: String,
    val arguments: Map<String, Any> = emptyMap()
)