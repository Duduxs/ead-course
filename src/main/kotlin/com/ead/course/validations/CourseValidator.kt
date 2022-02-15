package com.ead.course.validations

import com.ead.course.clients.AuthUserClient
import com.ead.course.dtos.CourseDTO
import com.ead.course.dtos.SubscriptionDTO
import com.ead.course.enums.UserType.STUDENT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.web.client.HttpStatusCodeException
import java.util.UUID

@Component
class CourseValidator(
    private val validator: Validator,
    private val client: AuthUserClient
) : Validator {

    override fun supports(clazz: Class<*>) = false

    override fun validate(target: Any, errors: Errors) {

        val entity = target as CourseDTO

        validator.validate(entity, errors)

        if (errors.hasErrors()) return

        validateInstructor(entity.instructorId, errors)

    }

    private fun validateInstructor(instructorId: UUID, errors: Errors) {

        try {

            val user = client.findById(SubscriptionDTO(instructorId))

            if (user.type == STUDENT) {
                errors.rejectValue(
                    CourseDTO::instructorId.name,
                    "UserForbidden",
                    "Students can't create a course"
                )
            }

        } catch (e: HttpStatusCodeException) {

            when (e.statusCode) {
                NOT_FOUND -> errors.rejectValue(
                    CourseDTO::instructorId.name,
                    "UserNotFound",
                    "User couldn't be found"
                )
                else -> errors.rejectValue(
                    CourseDTO::instructorId.name,
                    "UserError",
                    "Something went wrong when searching a user"
                )
            }

        }
    }
}