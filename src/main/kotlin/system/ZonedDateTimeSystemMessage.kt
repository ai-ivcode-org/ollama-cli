package org.ivcode.ai.ollama.system


import java.time.ZonedDateTime

class ZonedDateTimeSystemMessage: OllamaSystemMessage {
    override fun invoke(): String {
        val now = ZonedDateTime.now()

        return """
            Updated every request. All other messages may contain stale data.
            
            ```json
            {
                "zonedDateTime": "$now"
            }
            ```
        """.trimIndent()
    }
}
