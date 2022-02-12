package com.ead.course.core.extensions

fun String.inlineContent() = trimIndent().replace("\n", "")