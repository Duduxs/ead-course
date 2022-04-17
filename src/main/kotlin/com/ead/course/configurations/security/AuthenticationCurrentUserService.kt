package com.ead.course.configurations.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationCurrentUserService {
    val currentUser: UserDetailsImpl
        get() = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    val authentication: Authentication
        get() = SecurityContextHolder.getContext().authentication
}