package com.ead.course.services

import com.ead.course.core.exceptions.ConflictHttpException
import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.info
import com.ead.course.core.extensions.start
import com.ead.course.dtos.CourseDTO
import com.ead.course.dtos.NotificationCommandDTO
import com.ead.course.dtos.UserDTO
import com.ead.course.entities.Course
import com.ead.course.entities.Module
import com.ead.course.entities.User
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
import com.ead.course.mappers.updateEntity
import com.ead.course.publishers.NotificationCommandPublisher
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
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Root

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
    private val logger: KLogger,
    private val publisher: NotificationCommandPublisher
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

        val spec = Specification { root: Root<Course>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val user: Root<User> = query.from(User::class.java)
            val usersCourses: Expression<Collection<Course>> = user.get("courses")
            cb.and(cb.equal(user.get<Root<User>>("id"), userId), cb.isMember(root, usersCourses))
        }.and(defaultSpec)

        logger.end(this::findAll)

        return if (userId != null) {
            courseRepository.findAll(spec, pageable).map { it.toDTO() }
        } else {
            courseRepository.findAll(defaultSpec, pageable).map { it.toDTO() }
        }

    }

    @Transactional
    fun save(dto: CourseDTO): CourseDTO {

        logger.start(this::save, dto)

        val entity = courseRepository.save(dto.toDomain()).toDTO()

        logger.end(this::save, entity)

        return entity
    }

    @Transactional
    fun subscribeUserInCourse(courseId: UUID, userId: UUID) {

        logger.start(this::subscribeUserInCourse, parameters = arrayOf(courseId, userId))

        courseRepository.saveUserInCourse(courseId, userId)

        logger.end(this::subscribeUserInCourse)
    }

    fun sendNotification(course: CourseDTO, user: User) {

        try {

            logger.start(this::sendNotification, parameters = arrayOf(course, user))

            val dto = NotificationCommandDTO(
                title = "Welcome to the course : ${course.name}",
                message =  "${user.fullName}, your subscription was successfully done.",
                userId = user.id
            )

            publisher.publish(dto)

        } catch (e: RuntimeException) {
           logger.error { "Something went wrong: ${e.message}" }
        } finally {
            logger.end(this::sendNotification)
        }

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

        courseRepository.deleteCourseUserByCourseId(course.id)

        courseRepository.delete(course)

        logger.end(this::delete)
    }

    fun throwIfSubscriptionAlreadyExists(courseId: UUID, userId: UUID) {
        val exists = courseRepository.existsBy(
            courseId = courseId,
            userId = userId
        )

        if (exists) {
            throw ConflictHttpException("User is already enrolled in the course")
        }
    }

}
