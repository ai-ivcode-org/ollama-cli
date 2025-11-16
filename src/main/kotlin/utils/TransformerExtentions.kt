package org.ivcode.ai.synapp.utils

import io.github.ollama4j.models.chat.OllamaChatToolCalls
import io.github.ollama4j.tools.OllamaToolCallsFunction
import org.ivcode.ai.synapp.history.ToolCall

internal fun ToolCall.toOllamaChatToolCalls(): OllamaChatToolCalls {
    val function = OllamaToolCallsFunction()
    function.name = this.name
    function.arguments = this.arguments

    return OllamaChatToolCalls(function)
}

internal fun List<ToolCall>.toOllamaChatToolCalls(): List<OllamaChatToolCalls> =
    this.map { it.toOllamaChatToolCalls() }

internal fun OllamaChatToolCalls.toSynappToolCall() = ToolCall(
    name = this.function.name,
    arguments = this.function.arguments
)

internal fun List<OllamaChatToolCalls>.toSynappToolCalls() = this.map { it.toSynappToolCall() }