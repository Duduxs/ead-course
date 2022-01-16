package com.ead.course.services

import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.LessonDTO
import com.ead.course.entities.Lesson
import com.ead.course.entities.Module
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
import com.ead.course.mappers.updateEntity
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import mu.KLogger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Root

@Service
class LessonService(
    private val lessonRepository: LessonRepository,
    private val moduleRepository: ModuleRepository,
    private val logger: KLogger,
) {

    @Transactional(readOnly = true)
    fun findById(lessonId: UUID) = lessonRepository.findById(lessonId)
        .orElseThrow { NotFoundHttpException("Lesson with id $lessonId not found") }
        .toDTO()

    @Transactional(readOnly = true)
    fun findAll(
        moduleId: UUID,
        defaultSpec: Specification<Lesson>?,
        pageable: Pageable,
    ): Page<LessonDTO> {

        logger.start(this::findAll)

        val spec = Specification { root: Root<Lesson>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val lesson: Root<Lesson> = root
            val module: Root<Module> = query.from(Module::class.java)
            val moduleLessons: Expression<Collection<Lesson>> = module.get("lessons")
            cb.and(cb.equal(module.get<String>("id"), moduleId), cb.isMember(lesson, moduleLessons))
        }.and(defaultSpec)

        val result = lessonRepository.findAll(spec, pageable).map { it.toDTO() }

        logger.end(this::findAll)

        return result
    }

    @Transactional
    fun save(moduleId: UUID, dto: LessonDTO): LessonDTO {

        logger.start(this::save)

        val module = moduleRepository.findById(moduleId).orElseThrow {
            NotFoundHttpException("Module with id $moduleId not found")
        }

        val entity = lessonRepository.save(dto.toDomain(module))

        logger.end(this::save, entity)

        return entity.toDTO()
    }

    @Transactional
    fun update(lessonId: UUID, dto: LessonDTO): LessonDTO {

        logger.start(this::update)

        val lesson = lessonRepository.findById(lessonId)
            .orElseThrow { NotFoundHttpException("Lesson with id $lessonId not found") }

        val lessonUpdated = updateEntity(lesson, dto)

        lessonRepository.save(lessonUpdated)

        logger.end(this::update)

        return lessonUpdated.toDTO()
    }

    @Transactional
    fun deleteById(id: UUID) {

        logger.start(this::deleteById)

        try {
            lessonRepository.deleteById(id)
        } catch(e: RuntimeException) {
            throw NotFoundHttpException("Lesson with $id not found")
        } finally {
            logger.end(this::deleteById)
        }
    }

}
