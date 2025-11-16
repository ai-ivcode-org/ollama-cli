package org.ivcode.ai.ollama.agent

import io.github.ollama4j.Ollama
import io.github.ollama4j.models.chat.*
import org.ivcode.ai.ollama.history.OllamaHistoryManager
import org.ivcode.ai.ollama.system.OllamaSystemMessage
import java.util.*

class Ollama4jChatAgent(
    override val ollama: Ollama,
    override val model: String,
    systemMessages: List<OllamaSystemMessage>? = null,
    private val historyManager: OllamaHistoryManager,
): OllamaChatAgent {

    override val systemMessages: MutableList<OllamaSystemMessage> = systemMessages?.toMutableList() ?: mutableListOf()

    override fun chat(message: String, tokenHandler: OllamaChatTokenHandler?): OllamaChatResult {
        val messages = mutableListOf<OllamaChatMessage>().apply {
            // System info messages
            for (systemInfo in systemMessages.orEmpty()) {
                withMessage(systemInfo)
            }

            // Add existing history
            withMessages(historyManager.getMessages())

            // Add user message
            withMessage(OllamaChatMessageRole.USER, message)
        }

        val builder = OllamaChatRequest.builder()
            .withModel(model)
            .withMessages(messages)

        val response = ollama.chat(builder.build(), tokenHandler)

        historyManager.updateHistoryFromResponse(response)

        return response
    }

    override fun getSessionId(): UUID {
        return historyManager.id
    }

    override fun getChatHistory(): List<OllamaChatMessage> {
        return historyManager.getMessages()
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

        addMessages(collected)
    }

    private fun MutableList<OllamaChatMessage>.withMessage (systemMessage: OllamaSystemMessage) {
        val message = systemMessage()
        if(message.isNullOrBlank()) {
            return
        }

        withMessage(OllamaChatMessageRole.SYSTEM, message)
    }

    private fun MutableList<OllamaChatMessage>.withMessages (messages: List<OllamaChatMessage>) {
        addAll(messages)
    }

    private fun MutableList<OllamaChatMessage>.withMessage (
        role: OllamaChatMessageRole,
        content: String
    ) {
        add(OllamaChatMessage(
            /* role = */ role,
            /* response = */ content,
        ))
    }
}