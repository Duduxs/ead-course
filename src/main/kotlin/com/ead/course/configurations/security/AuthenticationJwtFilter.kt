package com.ead.course.configurations.security

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationJwtFilter : OncePerRequestFilter() {

    @Autowired
    private val jwtProvider: JwtProvider? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwtStr = getTokenHeader(request)
            if (jwtStr != null && jwtProvider!!.validateJwt(jwtStr)) {
                val userID = jwtProvider.getSubjectJwt(jwtStr)
                val rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles")
                val userDetails: UserDetails = UserDetailsImpl.build(UUID.fromString(userID), rolesStr)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Something went wrong: " + e.message)
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenHeader(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7)
        } else null
    }

    companion object : KLogging()
}