package com.ead.course.validations

import com.ead.course.dtos.CourseDTO
import com.ead.course.enums.UserType.STUDENT
import com.ead.course.services.UserService
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.util.UUID

@Component
class CourseValidator(
    private val validator: Validator,
    private val service: UserService
) : Validator {

    override fun supports(clazz: Class<*>) = false

    override fun validate(target: Any, errors: Errors) {

        val entity = target as CourseDTO

        validator.validate(entity, errors)

        if (errors.hasErrors()) return

        validateInstructor(entity.instructorId, errors)

    }

    private fun validateInstructor(instructorId: UUID, errors: Errors) {

        val instructor = service.findByIdOrNull(instructorId)

        if (instructor == null) {
            errors.rejectValue(
                CourseDTO::instructorId.name,
                "UserNotFound",
                "User couldn't be found"
            )
        } else if (instructor.type == STUDENT.name) {
            errors.rejectValue(
                CourseDTO::instructorId.name,
                "UserForbidden",
                "Students can't create a course"
            )
        }
    }
}