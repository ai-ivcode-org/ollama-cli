package org.ivcode.ai.ollama.history

class InMemoryHistoryManagerFactory: OllamaHistoryManagerFactory {
    override fun createHistoryManager(): OllamaHistoryManager = InMemoryHistoryManager()
}