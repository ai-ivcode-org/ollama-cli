package org.ivcode.ai.synapp.config

import io.github.ollama4j.tools.Tools
import org.ivcode.ai.synapp.agent.Ollama4jChatAgentFactory
import org.ivcode.ai.synapp.agent.OllamaChatAgentFactory
import org.ivcode.ai.synapp.annotations.OllamaController
import org.ivcode.ai.synapp.controller.ControllerParser
import org.ivcode.ai.synapp.core.Ollama4jFactory
import org.ivcode.ai.synapp.core.OllamaFactory
import org.ivcode.ai.synapp.history.InMemoryHistoryManagerFactory
import org.ivcode.ai.synapp.history.OllamaHistoryManagerFactory
import org.ivcode.ai.synapp.system.BasicSystemMessageFactory
import org.ivcode.ai.synapp.system.OllamaSystemMessage
import org.ivcode.ai.synapp.system.OllamaSystemMessageFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


const val PROPERTY_URL = "ollama.url"
const val PROPERTY_MODEL_NAME = "ollama.model"
const val PROPERTY_REQUEST_TIMEOUT = "ollama.requestTimeoutSeconds"

@Configuration
class OllamaConfig(
    @Value("\${$PROPERTY_URL:http://localhost:11434}") private val url: String,
    @Value("\${$PROPERTY_MODEL_NAME:gpt-oss:20b}") private val model: String,
    @Value("\${$PROPERTY_REQUEST_TIMEOUT:#{null}}") private val requestTimeoutSeconds: Long?,
) {

    @Bean("tool_services.system_messages")
    fun createOllamaToolServicesSystemMessages (
        @Qualifier("tool_services") toolControllers: Map<String, Any>,
    ): List<OllamaSystemMessage> {
        val systemMessages = mutableListOf<OllamaSystemMessage>()
        val parser = ControllerParser()

        toolControllers.values.forEach { v ->
            systemMessages.addAll(parser.parseController(v))
        }

        return systemMessages
    }

    @Bean
    @ConditionalOnMissingBean
    fun createSystemMessageFactory(
        @Qualifier("tool_services.system_messages") toolServiceSystemMessages: List<OllamaSystemMessage>,
        systemMessages: List<OllamaSystemMessage>?,
    ): OllamaSystemMessageFactory {
        return BasicSystemMessageFactory (
            systemMessages = (toolServiceSystemMessages + (systemMessages ?: emptyList())).distinct()
        )
    }


    @Bean("tool_services")
    fun ollamaToolServices (ctx: ApplicationContext): Map<String,Any> {
        return ctx.getBeansWithAnnotation(OllamaController::class.java)
    }

    @Bean
    @ConditionalOnMissingBean
    fun createOllamaFactory(
        @Qualifier("tool_services") toolServices: Map<String, Any>,
        tools: List<Tools.Tool>?
    ): OllamaFactory = Ollama4jFactory (
        url = url,
        model = model,
        requestTimeoutSeconds = requestTimeoutSeconds,
        toolServices = toolServices,
        tools = tools
    )

    @Bean
    @ConditionalOnMissingBean
    fun createHistoryManagerFactory(): OllamaHistoryManagerFactory = InMemoryHistoryManagerFactory()

    @Bean
    @ConditionalOnMissingBean
    fun createChatAgentFactory(
        ollamaFactory: OllamaFactory,
        historyManagerFactory: OllamaHistoryManagerFactory,
        systemMessageFactory: OllamaSystemMessageFactory
    ): OllamaChatAgentFactory = Ollama4jChatAgentFactory (
        model = model,
        ollamaFactory = ollamaFactory,
        historyManagerFactory = historyManagerFactory,
        systemMessageFactory = systemMessageFactory
    )
}