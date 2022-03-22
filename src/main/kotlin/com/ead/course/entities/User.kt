package com.ead.course.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_users")
class User(

    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID,

    @Column(nullable = false, unique = true, length = 50)
    val email: String,

    @Column(nullable = false, unique = true, length = 14)
    val cpf: String,

    @Column(nullable = false, length = 100)
    val fullName: String,

    @Column(nullable = false)
    val status: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = true)
    val imgUrl: String? = null,

    @field:JsonProperty(access = WRITE_ONLY)
    @ManyToMany(mappedBy = "users", fetch = LAZY)
    val courses: Set<Course> = HashSet()

)