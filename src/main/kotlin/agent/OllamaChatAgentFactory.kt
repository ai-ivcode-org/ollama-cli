package org.ivcode.ai.ollama.agent

interface OllamaChatAgentFactory {
    fun createOllamaSession(): OllamaChatAgent
}