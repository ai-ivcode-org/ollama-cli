package org.ivcode.ai.synapp.agent

import io.github.ollama4j.Ollama
import io.github.ollama4j.models.chat.*
import org.ivcode.ai.synapp.history.InMemoryHistoryManager
import org.ivcode.ai.synapp.history.OllamaHistoryManager
import org.ivcode.ai.synapp.history.OllamaHistoryMessage
import org.ivcode.ai.synapp.system.OllamaSystemMessage
import org.ivcode.ai.synapp.utils.toSynappToolCalls
import org.ivcode.ai.synapp.utils.withMessage

class Ollama4jChatAgent(
    private val ollama: Ollama,
    private val model: String,
    private val systemMessages: List<OllamaSystemMessage>? = null,
    private val historyManager: OllamaHistoryManager,
): OllamaChatAgent {
    override fun chat(message: String, tokenHandler: OllamaChatTokenHandler?): OllamaChatResult {
        val builder = OllamaChatRequest.builder().withModel(model)

        // System info messages
        for (systemInfo in systemMessages.orEmpty()) {
            builder.withMessage(OllamaChatMessageRole.SYSTEM, systemInfo())
        }

        // Add existing history
        builder.withHistory()

        // Add user message
        builder.withMessage(OllamaChatMessageRole.USER, message)

        val response = ollama.chat(builder.build(), tokenHandler)

        historyManager.updateHistoryFromResponse(response)

        return response
    }

    private fun OllamaChatRequest.withHistory() {
        historyManager.getMessages().forEach { message ->
            withMessage(message)
        }
    }

    private fun OllamaHistoryManager.updateHistoryFromResponse(response: OllamaChatResult) {
        val collected = mutableListOf<OllamaChatMessage>()

        for (msg in response.chatHistory.asReversed()) {
            val roleName = msg.role.roleName
            collected.add(msg)

            if (roleName == OllamaChatMessageRole.USER.roleName) {
                break
            }
        }

        // Restore chronological order (oldest first) before setting
        collected.reverse()

        addMessages(
            collected.map {
                OllamaHistoryMessage(
                    role = it.role.roleName,
                    content = it.response,
                    toolCalls = it.toolCalls?.toSynappToolCalls()
                )
            }
        )
    }
}


fun Ollama.createSession(
    model: String,
    systemMessages: List<OllamaSystemMessage>? = null,
): Ollama4jChatAgent {
    return Ollama4jChatAgent(
        ollama = this,
        model = model,
        systemMessages = systemMessages,
        historyManager = InMemoryHistoryManager(),
    )
}