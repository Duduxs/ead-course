package com.ead.course.services

import com.ead.course.repositories.LessonRepository
import org.springframework.stereotype.Service

@Service
class LessonService(
    val repository: LessonRepository
)