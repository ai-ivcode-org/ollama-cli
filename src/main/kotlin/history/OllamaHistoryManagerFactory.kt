package org.ivcode.ai.synapp.history

interface OllamaHistoryManagerFactory {
    fun createHistoryManager(): OllamaHistoryManager
}