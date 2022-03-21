package com.ead.course.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(
    name = "tb_users"
)
class User(

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, updatable = false)
    val id: UUID? = null,

)