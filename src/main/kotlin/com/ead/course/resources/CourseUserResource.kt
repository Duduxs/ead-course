package com.ead.course.resources

import com.ead.course.clients.CourseClient
import com.ead.course.core.extensions.makeLogged
import com.ead.course.dtos.UserDTO
import mu.KLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserResource(
    private val client: CourseClient
) {

    @GetMapping("courses/{courseId}/users")
    fun findAllBy(
        @PathVariable("courseId") courseId: UUID,
        @PageableDefault(sort = ["id"], direction = ASC) pageable: Pageable
    ): ResponseEntity<Page<UserDTO>> = logger.makeLogged(this::findAllBy, parameters = arrayOf(courseId)) {

        val result = client.findAllBy(courseId, pageable)

        ResponseEntity.ok(result)

    }

    companion object : KLogging()
}