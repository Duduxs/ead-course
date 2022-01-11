package com.ead.course.resources

import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.makeLogged
import com.ead.course.core.extensions.start
import com.ead.course.dtos.LessonDTO
import com.ead.course.services.LessonService
import mu.KLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.validation.Valid

@RestController
@CrossOrigin("*", maxAge = 3600)
class LessonResource(
    private val service: LessonService,
    private val logger: KLogger
) {

    @GetMapping("lessons/{lessonId}")
    fun findById(
        @PathVariable lessonId: UUID
    ): ResponseEntity<LessonDTO> = logger.makeLogged(function = this::findById, parameters = arrayOf(lessonId)) {

            val entity = service.findById(lessonId)

            ResponseEntity.ok(entity)

        }

    @GetMapping("/modules/{moduleId}/lessons")
    fun findAllInModules(
        @PathVariable moduleId: UUID)
    : ResponseEntity<Collection<LessonDTO>> {

        logger.start(this::findAllInModules, parameters = arrayOf(moduleId))

        val entities = service.findAll(moduleId)

        logger.end(this::findAllInModules)

        return ResponseEntity.ok(entities)

    }

    @PostMapping("modules/{moduleId}/lessons")
    fun create(@PathVariable moduleId: UUID, @Valid @RequestBody dto: LessonDTO): ResponseEntity<LessonDTO> {

        logger.start(this::create, dto, parameters = arrayOf(moduleId))

        val entity = service.save(moduleId, dto)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.id)
            .toUri()

        logger.end(this::create)

        return ResponseEntity.created(uri).body(entity)

    }

    @PutMapping("lessons/{lessonId}")
    fun update(
        @PathVariable lessonId: UUID,
        @Valid @RequestBody dto: LessonDTO
    ): ResponseEntity<LessonDTO> {

        logger.start(this::update, body = dto, parameters = arrayOf(lessonId))

        val updatedEntity = service.update(lessonId, dto)

        logger.end(this::update, updatedEntity)

        return ResponseEntity.ok(updatedEntity)

    }

    @DeleteMapping("lessons/{lessonId}")
    fun delete(
        @PathVariable lessonId: UUID
    ): ResponseEntity<Void> = logger.makeLogged(function = this::delete, parameters = arrayOf(lessonId)) {

            service.deleteById(lessonId)

            ResponseEntity.noContent().build()

        }
}