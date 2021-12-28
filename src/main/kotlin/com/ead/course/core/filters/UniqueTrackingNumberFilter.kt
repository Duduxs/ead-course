package com.ead.course.core.filters

import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UniqueTrackingNumberFilter : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ID = "X-Request-Id"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestId = request.getHeader(REQUEST_ID).orEmpty()

        if (requestId.isNotBlank())
            MDC.put(REQUEST_ID, requestId)
        else
            MDC.put(REQUEST_ID, UUID.randomUUID().toString())

        filterChain.doFilter(request, response)
    }

}
