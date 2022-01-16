package com.ead.course.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/*
    This is an example where we know how to configure date in all application.
    @Configuration
 */
class DateExampleConfig {

    private val iso8601UTCFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addSerializer(
            LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(iso8601UTCFormat)
            )
        )

        return ObjectMapper()
            .registerModule(module)
    }
}