package com.ead.course.publishers

import com.ead.course.dtos.NotificationCommandDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody

@Component
class NotificationCommandPublisher @Autowired constructor(
    private val template: RabbitTemplate
) {

    @Value("\${ead.broker.exchange.notificationCommand.name}")
    private val notificationCommandExchange: String = ""

    @Value("\${ead.broker.key.notificationCommand.name}")
    private val notificationCommandKey: String = ""

    fun publish(@RequestBody dto: NotificationCommandDTO) {
        template.convertAndSend(notificationCommandExchange, notificationCommandKey, dto)
    }

}