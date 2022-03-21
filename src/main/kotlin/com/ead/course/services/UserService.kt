package com.ead.course.services

import com.ead.course.repositories.UserRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {


    companion object : KLogging()
}