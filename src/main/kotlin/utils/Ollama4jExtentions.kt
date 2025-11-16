package org.ivcode.ai.synapp.utils

import io.github.ollama4j.models.chat.OllamaChatMessageRole
import io.github.ollama4j.models.chat.OllamaChatRequest
import org.ivcode.ai.synapp.history.OllamaHistoryMessage

internal fun OllamaChatRequest.withMessage(message: OllamaHistoryMessage) = apply {
    withMessage (
        OllamaChatMessageRole.getRole(message.role),
        message.content,
        message.toolCalls?.toOllamaChatToolCalls(),
    )
}