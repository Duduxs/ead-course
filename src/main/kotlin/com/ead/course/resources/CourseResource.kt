package com.ead.course.resources

import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.makeLogged
import com.ead.course.core.extensions.start
import com.ead.course.dtos.CourseDTO
import com.ead.course.services.CourseService
import mu.KotlinLogging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/courses")
@CrossOrigin("*", maxAge = 3600)
class CourseResource(
    private val service: CourseService
) {

    private val log = logger {}

    @PostMapping
    fun create(@Valid @RequestBody dto: CourseDTO): ResponseEntity<CourseDTO> = log.makeLogged(this::create)
    {
        val entity = service.save(dto)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.id)
            .toUri()

        ResponseEntity.created(uri).body(entity)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        log.start(this::delete, parameters = arrayOf(id))

        service.delete(id)

        log.end(this::delete)

        return ResponseEntity.noContent().build()
    }
}