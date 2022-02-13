package com.ead.course.clients

import com.ead.course.core.exceptions.ForbiddenHttpException
import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.inlineContent
import com.ead.course.core.extensions.start
import com.ead.course.dtos.ResponsePageDTO
import com.ead.course.dtos.SubscriptionDTO
import com.ead.course.dtos.UserDTO
import com.ead.course.enums.UserStatus.BLOCKED
import mu.KotlinLogging.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.UUID

@Component
class AuthUserClient(
    private val template: RestTemplate
) {

    @Value("\${ead.api.url.authuser}")
    private val authUserUri: String = ""

    companion object {
        private val logger = logger {}
    }

    fun findAllBy(courseId: UUID, pageable: Pageable): Page<UserDTO> {

        val url: String =
            """
                $authUserUri/users?
                courseId=$courseId
                &page=${pageable.pageNumber}
                &size=${pageable.pageSize}
                &sort=${pageable.sort.toString().replace(": ", ",")}
            """.inlineContent()

        logger.start(this::findAllBy, parameters = arrayOf(url))

        try {

            val responseType = object : ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {}

            val result = template.exchange(url, GET, null, responseType)

            val userDTO: List<UserDTO> = result.body?.content ?: emptyList()

            logger.info { "Number of elements -> ${userDTO.size}" }

            logger.end(this::findAllBy)

            return PageImpl(userDTO)

        } catch (e: HttpStatusCodeException) {

            logger.error { "Something went wrong: ${e.message}"}
            throw e

        }
    }

    fun findById(dto: SubscriptionDTO): UserDTO {

        val url = "$authUserUri/users/${dto.userId}"

        logger.start(
            this::findById,
            message = "Starting ${this::findById.name} with url $url",
            parameters = arrayOf(dto.userId)
        )

        try {

            val responseType = UserDTO::class.java

            val result = template.exchange(url, GET, null, responseType)

            val userDTO = result.body!!

            if(userDTO.status == BLOCKED) throw ForbiddenHttpException("User is blocked")

            return userDTO

        } catch(e: HttpStatusCodeException) {

            logger.error { "Something went wrong: ${e.message}" }

            when(e.statusCode) {
                NOT_FOUND -> throw NotFoundHttpException("User not found")
                else -> throw e
            }

        } finally {

            logger.end(this::findById)

        }

    }
}