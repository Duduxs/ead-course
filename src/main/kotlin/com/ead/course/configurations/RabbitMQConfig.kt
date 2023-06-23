package com.ead.course.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
    private val connectionFactory: CachingConnectionFactory
) {
    @Bean
    fun rabbitTemplate() = RabbitTemplate(connectionFactory)
        .also { it.messageConverter = messageConverter() }

    @Bean
   fun messageConverter(): Jackson2JsonMessageConverter {
        val mapper = ObjectMapper()
            .registerModule(JavaTimeModule())

        return Jackson2JsonMessageConverter(mapper)
    }
}