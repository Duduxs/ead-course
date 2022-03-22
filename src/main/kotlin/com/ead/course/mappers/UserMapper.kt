package com.ead.course.mappers

import com.ead.course.dtos.UserDTO
import com.ead.course.entities.User

fun User.toDTO() = UserDTO(this)
