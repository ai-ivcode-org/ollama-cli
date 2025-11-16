package org.ivcode.ai.ollama.history

interface OllamaHistoryManagerFactory {
    fun createHistoryManager(): OllamaHistoryManager
}