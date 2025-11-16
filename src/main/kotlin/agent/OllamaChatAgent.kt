package org.ivcode.ai.synapp.agent

import io.github.ollama4j.models.chat.OllamaChatResult
import io.github.ollama4j.models.chat.OllamaChatTokenHandler
import io.github.ollama4j.agent.Agent

/**
 * Agent interface for chatting with Ollama models. This is similar to Ollama4j's [Agent] interface but
 * add hooks for customization. It allows for plugin support in defining dynamic system messages, history
 * management,and tooling.
 */
interface OllamaChatAgent {
    fun chat(message: String, tokenHandler: OllamaChatTokenHandler? = null): OllamaChatResult
}