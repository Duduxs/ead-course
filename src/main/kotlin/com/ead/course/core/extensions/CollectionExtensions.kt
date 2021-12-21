package com.ead.course.core.extensions

import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

fun Collection<Annotation>.lookForAnyHttpMethodAnnotation(): HttpMethod? {
    val annotations = mutableListOf<HttpMethod?>()

    for (element in this) {
        annotations.add(
            when (element) {
                is GetMapping -> HttpMethod.GET
                is PostMapping -> HttpMethod.POST
                is PutMapping -> HttpMethod.PUT
                is PatchMapping -> HttpMethod.PATCH
                is DeleteMapping -> HttpMethod.DELETE
                else -> null
            }
        )
    }

    return annotations.filterNotNull().firstOrNull()
}