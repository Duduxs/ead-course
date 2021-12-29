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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping("{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<CourseDTO> =
        log.makeLogged(function = this::findById, parameters = arrayOf(id)) {

        val entity = service.findById(id)

        ResponseEntity.ok(entity)

    }

    @GetMapping
    fun findAll(): ResponseEntity<Collection<CourseDTO>> {

        log.start(this::findAll)

        val entities = service.findAll()

        log.end(this::findAll)

        return ResponseEntity.ok(entities)

    }

    @PostMapping
    fun create(@Valid @RequestBody dto: CourseDTO): ResponseEntity<CourseDTO> = log.makeLogged(this::create, dto) {

        val entity = service.save(dto)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.id)
            .toUri()

        ResponseEntity.created(uri).body(entity)

    }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody dto: CourseDTO, @PathVariable id: UUID): ResponseEntity<CourseDTO> {

        log.start(this::update, body = dto, parameters = arrayOf(id))

        val updatedEntity = service.update(id, dto)

        log.end(this::update, updatedEntity)

        return ResponseEntity.ok(updatedEntity)

    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        log.start(this::delete, parameters = arrayOf(id))

        service.delete(id)

        log.end(this::delete)

        return ResponseEntity.noContent().build()

    }
}