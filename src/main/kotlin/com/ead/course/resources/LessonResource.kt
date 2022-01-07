package com.ead.course.resources

import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.LessonDTO
import com.ead.course.services.LessonService
import mu.KLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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

//    @GetMapping("modules/{moduleId}")
//    fun findById(@PathVariable moduleId: UUID): ResponseEntity<LessonDTO> =
//        logger.makeLogged(function = this::findById, parameters = arrayOf(moduleId)) {
//
//            val entity = service.findById(moduleId)
//
//            ResponseEntity.ok(entity)
//
//        }
//
//    @GetMapping("/courses/{courseId}/modules")
//    fun findAllInCourse(@PathVariable courseId: UUID): ResponseEntity<Collection<LessonDTO>> {
//
//        logger.start(this::findAllInCourse)
//
//        val entities = service.findAll(courseId)
//
//        logger.end(this::findAllInCourse)
//
//        return ResponseEntity.ok(entities)
//
//    }

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

//    @PutMapping("modules/{moduleId}")
//    fun update(@PathVariable moduleId: UUID, @Valid @RequestBody dto: LessonDTO): ResponseEntity<LessonDTO> {
//
//        logger.start(this::update, body = dto, parameters = arrayOf(moduleId))
//
//        val updatedEntity = service.update(moduleId, dto)
//
//        logger.end(this::update, updatedEntity)
//
//        return ResponseEntity.ok(updatedEntity)
//
//    }
//
//    @DeleteMapping("modules/{moduleId}")
//    fun delete(@PathVariable moduleId: UUID): ResponseEntity<Void> =
//        logger.makeLogged(function = this::delete, parameters = arrayOf(moduleId)) {
//
//            service.deleteById(moduleId)
//
//            ResponseEntity.noContent().build()
//
//        }
}