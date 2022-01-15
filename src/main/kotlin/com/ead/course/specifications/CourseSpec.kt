package com.ead.course.specifications

import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@And(
    Spec(path = "name", spec = Like::class),
    Spec(path = "level", spec = Equal::class),
    Spec(path = "status", spec = Equal::class)
)
@Target(VALUE_PARAMETER)
@Retention(RUNTIME)
@MustBeDocumented
annotation class CourseSpec
