package org.ivcode.ai.synapp.system

interface OllamaSystemMessageFactory {
    fun createSystemMessages(): List<OllamaSystemMessage>
}