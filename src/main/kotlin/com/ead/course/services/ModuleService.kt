package com.ead.course.services

import com.ead.course.repositories.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleService(
    val repository: ModuleRepository
)