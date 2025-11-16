package org.ivcode.ai.ollama.agent

import org.ivcode.ai.ollama.core.OllamaFactory
import org.ivcode.ai.ollama.history.OllamaHistoryManagerFactory
import org.ivcode.ai.ollama.system.OllamaSystemMessageFactory

class Ollama4jChatAgentFactory (
    private val model: String,
    private val ollamaFactory: OllamaFactory,
    private val historyManagerFactory: OllamaHistoryManagerFactory,
    private val systemMessageFactory: OllamaSystemMessageFactory
): OllamaChatAgentFactory {
    override fun createOllamaSession(): OllamaChatAgent {
        val ollama = ollamaFactory.createOllama()
        val historyManager = historyManagerFactory.createHistoryManager()
        val systemMessages = systemMessageFactory.createSystemMessages()

        return Ollama4jChatAgent(
            model = model,
            ollama = ollama,
            historyManager = historyManager,
            systemMessages = systemMessages,
        )
    }
}