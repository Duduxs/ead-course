package com.ead.course.mappers

import com.ead.course.dtos.UserDTO
import com.ead.course.dtos.UserEventDTO
import com.ead.course.entities.User

fun UserEventDTO.toDomain() = User(
    id = id,
    cpf = cpf,
    email = email,
    fullName = fullName,
    status = status,
    type = type,
    imgUrl = imgUrl
)

fun User.toDTO() = UserDTO(this)