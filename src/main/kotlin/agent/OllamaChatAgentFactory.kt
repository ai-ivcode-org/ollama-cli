package org.ivcode.ai.synapp.agent

interface OllamaChatAgentFactory {
    fun createOllamaSession(): OllamaChatAgent
}