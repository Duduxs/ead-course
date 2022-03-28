package com.ead.course.services

import com.ead.course.core.exceptions.ForbiddenHttpException
import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.UserDTO
import com.ead.course.entities.Course
import com.ead.course.entities.User
import com.ead.course.enums.UserStatus.BLOCKED
import com.ead.course.mappers.toDTO
import com.ead.course.repositories.UserRepository
import mu.KLogging
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
class UserService(
    private val repository: UserRepository,
) {

    fun findByIdOrNull(id: UUID): User? = repository.findById(id).orElse(null)

    @Transactional(readOnly = true)
    fun findById(id: UUID): User {

        logger.start(this::findById, parameters = arrayOf(id))

        val entity = findByIdOrNull(id) ?: throw NotFoundHttpException("User with id $id not found")

        logger.end(this::findById)

        return entity
    }

    @Transactional(readOnly = true)
    fun findAll(
        defaultSpec: Specification<User>?,
        courseId: UUID,
        pageable: Pageable,
    ): Page<UserDTO> {

        logger.start(this::findAll)

        val spec = Specification { root: Root<User>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val course: Root<Course> = query.from(Course::class.java)
            val coursesUsers: Expression<Collection<User>> = course.get("users")
            cb.and(cb.equal(course.get<Root<User>>("id"), courseId), cb.isMember(root, coursesUsers))
        }.and(defaultSpec)

        logger.end(this::findAll)

        return repository.findAll(spec, pageable).map { it.toDTO() }

    }

    @Transactional
    fun save(user: User): User {

        logger.start(this::save, message = "Begging to save/update user with cpf ${user.cpf}")

        val savedEntity = repository.save(user)

        logger.end(this::save)

        return savedEntity

    }

    @Transactional
    fun deleteBy(id: UUID) {

        logger.start(this::deleteBy, parameters = arrayOf(id))

        val user = findById(id)

        repository.delete(user)

        logger.end(this::deleteBy)

    }

    fun throwIfUserIsBlocked(user: User) {
        if (user.status == BLOCKED.name)
            throw ForbiddenHttpException("User is blocked")
    }

    companion object : KLogging()
}