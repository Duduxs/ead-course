package com.ead.course.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class AuditorConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAwareImpl()

    inner class AuditorAwareImpl : AuditorAware<String> {
        override fun getCurrentAuditor(): Optional<String> = Optional.of("Admin")
    }
}