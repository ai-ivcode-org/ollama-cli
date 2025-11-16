package org.ivcode.ai.synapp.system

class BasicSystemMessageFactory(
    private val systemMessages: List<OllamaSystemMessage>
): OllamaSystemMessageFactory {
    override fun createSystemMessages(): List<OllamaSystemMessage> = systemMessages
}