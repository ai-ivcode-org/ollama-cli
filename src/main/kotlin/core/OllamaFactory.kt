package org.ivcode.ai.synapp.core

import io.github.ollama4j.Ollama

interface OllamaFactory {
    fun createOllama(): Ollama
}