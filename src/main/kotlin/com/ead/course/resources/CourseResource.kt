package com.ead.course.resources

import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.makeLogged
import com.ead.course.core.extensions.start
import com.ead.course.dtos.CourseDTO
import com.ead.course.entities.Course
import com.ead.course.services.CourseService
import com.ead.course.validations.CourseValidator
import mu.KLogger
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/courses")
@CrossOrigin("*", maxAge = 3600)
class CourseResource(
    private val service: CourseService,
    private val logger: KLogger,
    private val validator: CourseValidator
) {

    @GetMapping("{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<CourseDTO> =
        logger.makeLogged(function = this::findById, parameters = arrayOf(id)) {

            val entity = service.findById(id)

            ResponseEntity.ok(entity)

        }

    @GetMapping
    fun findAll(
        @And(
            Spec(path = "name", spec = Like::class),
            Spec(path = "level", spec = Equal::class),
            Spec(path = "status", spec = Equal::class)
        ) spec: Specification<Course>?,
        @RequestParam userId: UUID?,
        @PageableDefault(direction = ASC) pageable: Pageable,
    ): ResponseEntity<Page<CourseDTO>> {

        logger.start(this::findAll)

        val entities = service.findAll(spec, userId, pageable)

        logger.end(this::findAll)

        return ResponseEntity.ok(entities)

    }

    @PostMapping
    fun create(
        @RequestBody dto: CourseDTO,
        errors: Errors
    ): ResponseEntity<Any> = logger.makeLogged(this::create, dto) {

        validator.validate(dto, errors)

        if(errors.hasErrors()) {
            ResponseEntity.badRequest().body(errors.allErrors)
        } else {

            val entity = service.save(dto)

            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.id)
                .toUri()

            ResponseEntity.created(uri).body(entity)
        }
    }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody dto: CourseDTO, @PathVariable id: UUID): ResponseEntity<CourseDTO> {

        logger.start(this::update, body = dto, parameters = arrayOf(id))

        val updatedEntity = service.update(id, dto)

        logger.end(this::update, updatedEntity)

        return ResponseEntity.ok(updatedEntity)

    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        logger.start(this::delete, parameters = arrayOf(id))

        service.deleteById(id)

        logger.end(this::delete)

        return ResponseEntity.noContent().build()

    }
}