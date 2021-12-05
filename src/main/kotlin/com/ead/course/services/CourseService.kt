package com.ead.course.services

import com.ead.course.repositories.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(
    val repository: CourseRepository
)