package com.ead.course.resources

import com.ead.course.core.extensions.makeLogged
import com.ead.course.dtos.CourseDTO
import com.ead.course.services.CourseService
import mu.KotlinLogging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/courses")
@CrossOrigin("*", maxAge = 3600)
class CourseResource(
    private val service: CourseService
) {

    private val log = logger {}

    @PostMapping
    fun create(@Valid @RequestBody dto: CourseDTO): ResponseEntity<Any> = log.makeLogged(this::create)
    {
        val entity = service.save(dto)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.id)
            .toUri()

        ResponseEntity.created(uri).body(entity)
    }
}