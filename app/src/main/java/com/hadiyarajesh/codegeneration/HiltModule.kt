package com.hadiyarajesh.codegeneration

import com.hadiyarajesh.annotations.GenerateBinding
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * As we're using annotation processor to generate Interface-Implementation binding,
 * we don't need to write it manually.
 * When we use [GenerateBinding] annotation, [GenerateBindingProcessor] will generate code
 * at compile time at following location.
 * /app/build/generated/source/kapt/debug/com/hadiyarajesh/codegeneration/
 */
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class MyRepositoryModule {
//    @Binds
//    abstract fun bindMyRepository(impl: MyRepositoryImpl): MyRepository
//}

