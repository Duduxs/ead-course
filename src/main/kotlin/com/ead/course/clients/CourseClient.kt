package com.ead.course.clients

import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.inlineContent
import com.ead.course.core.extensions.start
import com.ead.course.dtos.ResponsePageDTO
import com.ead.course.dtos.UserDTO
import mu.KotlinLogging.logger
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod.*
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.UUID

@Component
class CourseClient(
    private val template: RestTemplate
) {

    companion object {
        private val logger = logger {}
        private const val REQUEST_URI: String = "http://localhost:8087"
    }

    fun findAllBy(courseId: UUID, pageable: Pageable): Page<UserDTO> {

        val url: String =
            """
                $REQUEST_URI/users?
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
}