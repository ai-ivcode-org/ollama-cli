package org.ivcode.ai.synapp.history

class InMemoryHistoryManagerFactory: OllamaHistoryManagerFactory {
    override fun createHistoryManager(): OllamaHistoryManager = InMemoryHistoryManager()
}