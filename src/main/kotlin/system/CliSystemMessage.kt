package org.ivcode.ai.synapp.system

internal class CliSystemMessage: OllamaSystemMessage {
    override fun invoke(): String =
        "You are a command line interface (CLI) assistant. You will format your responses to be read from the terminal. The line will be no longer than 80 characters."
}