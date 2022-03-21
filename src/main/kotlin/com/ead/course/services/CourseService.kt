package com.ead.course.services

import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.info
import com.ead.course.core.extensions.start
import com.ead.course.dtos.CourseDTO
import com.ead.course.entities.Course
import com.ead.course.entities.Module
import com.ead.course.entities.User
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
import com.ead.course.mappers.updateEntity
import com.ead.course.repositories.CourseRepository
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
import javax.persistence.criteria.Join
import javax.persistence.criteria.Root

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
    private val logger: KLogger,
) {

    @Transactional(readOnly = true)
    fun findById(id: UUID) = courseRepository.findById(id)
        .orElseThrow { NotFoundHttpException("Course with id $id not found") }
        .toDTO()

    @Transactional(readOnly = true)
    fun findAll(
        defaultSpec: Specification<Course>?,
        userId: UUID?,
        pageable: Pageable,
    ): Page<CourseDTO> {

        logger.start(this::findAll)

        val result = courseRepository.findAll(defaultSpec, pageable).map { it.toDTO() }

        logger.end(this::findAll)

        return result

    }

    @Transactional
    fun save(dto: CourseDTO): CourseDTO {

        logger.start(this::save, dto)

        val entity = courseRepository.save(dto.toDomain()).toDTO()

        logger.end(this::save, entity)

        return entity
    }

    @Transactional
    fun update(id: UUID, dto: CourseDTO): CourseDTO {

        logger.start(this::update, dto, parameters = arrayOf(id))

        val course = courseRepository.findById(id).orElseThrow { NotFoundHttpException("Course with id $id not found") }

        val courseUpdated = updateEntity(course, dto)

        courseRepository.save(courseUpdated)

        logger.end(this::update)

        return courseUpdated.toDTO()
    }

    @Transactional
    fun deleteById(id: UUID) {

        logger.start(this::deleteById, parameters = arrayOf(id))

        val course = courseRepository.findById(id).orElseThrow { NotFoundHttpException("Course with id $id not found") }

        delete(course)

        logger.end(this::deleteById)
    }

    private fun delete(course: Course) {

        logger.start(this::delete, parameters = arrayOf(course.id))

        val modules: Collection<Module> = moduleRepository.findAllModulesBy(course.id)

        logger.info(this::delete, message = "modules size in course ${modules.size}")

        if (modules.isNotEmpty()) {
            for (module in modules) {
                val lessons = lessonRepository.findAllLessonsBy(module.id!!)

                logger.info(this::delete, message = "lessons size in module id ${module.id} ${lessons.size}")

                if (lessons.isNotEmpty()) lessonRepository.deleteAll(lessons)
            }
            moduleRepository.deleteAll(modules)
        }

        courseRepository.delete(course)

        logger.end(this::delete)
    }
}
