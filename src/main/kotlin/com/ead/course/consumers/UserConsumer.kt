package com.ead.course.consumers

import com.ead.course.dtos.UserEventDTO
import com.ead.course.enums.ActionType.CREATE
import com.ead.course.enums.ActionType.valueOf
import com.ead.course.mappers.toDomain
import com.ead.course.services.UserService
import org.springframework.amqp.core.ExchangeTypes.FANOUT
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UserConsumer(
    private val service: UserService
) {

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "\${ead.broker.queue.userEvent.name}", durable = "true"),
                exchange = Exchange(
                    value = "\${ead.broker.exchange.userEvent.name}",
                    type = FANOUT,
                    ignoreDeclarationExceptions = "true"
                )
            )]
    )
    fun listen(@Payload dto: UserEventDTO) {

        val entity = dto.toDomain()

        when(valueOf(dto.actionType)) {

            CREATE -> service.save(entity)
            else -> throw IllegalArgumentException("Operation not mapped!")

        }

    }

}