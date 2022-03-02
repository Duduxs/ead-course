package com.ead.course.services

import com.ead.course.clients.AuthUserClient
import com.ead.course.core.exceptions.ConflictHttpException
import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.CourseDTO
import com.ead.course.dtos.SubscriptionDTO
import com.ead.course.entities.CourseUser
import com.ead.course.mappers.toDomainCourseUser
import com.ead.course.repositories.CourseUserRepository
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CourseUserService(
    private val courseUserRepository: CourseUserRepository,
    private val client: AuthUserClient,
) {

    @Transactional
    fun saveBy(courseDTO: CourseDTO, subscriptionDTO: SubscriptionDTO): CourseUser {

        logger.start(this::saveBy)

        throwIfUserIsAlreadyRegistered(courseDTO, subscriptionDTO)

        val entity = courseDTO.toDomainCourseUser(subscriptionDTO)
            .also { courseUserRepository.save(it) }

        client.subscribeUserInCourse(courseDTO.id, subscriptionDTO.userId)

        logger.end(this::saveBy)

        return entity

    }

    @Transactional
    fun deleteBy(userId: UUID) {

        logger.start(this::deleteBy, parameters = arrayOf(userId))

        if (!courseUserRepository.existsByUserId(userId)) {
            throw NotFoundHttpException("CourseUser not found")
        }

        courseUserRepository.deleteAllByUserId(userId)

        logger.end(this::deleteBy)

    }

    private fun throwIfUserIsAlreadyRegistered(courseDTO: CourseDTO, subscriptionDTO: SubscriptionDTO) {

        logger.start(this::throwIfUserIsAlreadyRegistered)

        if(courseUserRepository.existsByCourseAndUserId(courseDTO.id, subscriptionDTO.userId)) {
            throw ConflictHttpException("This user is already registered in course ${courseDTO.id}")
        }

        logger.end(this::throwIfUserIsAlreadyRegistered)

    }

    companion object : KLogging()
}