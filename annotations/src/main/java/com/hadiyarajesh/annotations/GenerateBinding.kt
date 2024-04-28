package com.hadiyarajesh.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class GenerateBinding(
    val type: KClass<*>
)
