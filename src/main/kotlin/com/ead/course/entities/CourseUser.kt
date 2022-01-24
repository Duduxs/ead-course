package com.ead.course.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "tb_courses_user")
class CourseUser(

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false)
    val id: UUID,

    @ManyToOne(fetch = LAZY, optional = false)
    val course: Course,

    @Column(nullable = false)
    val userId: UUID

)