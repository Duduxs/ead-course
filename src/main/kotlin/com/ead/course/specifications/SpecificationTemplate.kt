package com.ead.course.specifications

import com.ead.course.entities.Course
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

class SpecificationTemplate {

    @And(
        Spec(path = "name", spec = Like::class),
        Spec(path = "level", spec = Equal::class),
        Spec(path = "status", spec = Equal::class)
    )
    interface CourseSpec: Specification<Course>
}