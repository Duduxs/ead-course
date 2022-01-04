package com.ead.course.configurations

import mu.KLogger
import mu.KotlinLogging.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KotlinLoggingConfig {

    @Bean
    fun produce(): KLogger = logger {}
}