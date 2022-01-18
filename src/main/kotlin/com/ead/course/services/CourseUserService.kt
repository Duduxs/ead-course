package com.ead.course.services

import com.ead.course.repositories.CourseUserRepository
import org.springframework.stereotype.Service

@Service
class CourseUserService(
    private val courseUserRepository: CourseUserRepository,
)