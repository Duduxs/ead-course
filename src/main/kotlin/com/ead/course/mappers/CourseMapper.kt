package com.ead.course.mappers

import com.ead.course.dtos.CourseDTO
import com.ead.course.dtos.SubscriptionDTO
import com.ead.course.entities.Course
import com.ead.course.entities.CourseUser

fun CourseDTO.toDomainCourseUser(subscriptionDTO: SubscriptionDTO) = CourseUser(
    course = this.toDomain(),
    userId = subscriptionDTO.userId
)

fun CourseDTO.toDomain() = Course(
    id = this.id,
    name = this.name,
    description = this.description,
    imageUrl = this.imgUrl,
    status = this.status,
    instructorId = this.instructorId,
    level = this.level
)

fun Course.toDTO() = CourseDTO(this)

fun updateEntity(course: Course, dto: CourseDTO) = Course(
    id = course.id,
    name = dto.name,
    description = dto.description,
    imageUrl = dto.imgUrl,
    status = dto.status,
    instructorId = dto.instructorId,
    level = dto.level,
)