package com.ead.course.dtos

import com.ead.course.entities.Module
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ModuleDTO(

    @NotNull
    val id: UUID,

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val description: String
) {
    constructor(module: Module) : this (
        id = module.id ?: UUID.randomUUID(),
        title = module.title,
        description = module.description
    )
}