package com.hadiyarajesh.codegeneration.di

import com.hadiyarajesh.annotations.GenerateBinding

//import com.hadiyarajesh.codegeneration.repository.MyRepository
//import com.hadiyarajesh.codegeneration.repository.MyRepositoryImpl
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent

/**
 * As we're using annotation processor to generate Interface-Implementation binding,
 * we don't need to write it manually.
 * When we use [GenerateBinding] annotation, [com.hadiyarajesh.processors.GenerateBindingProcessor] will generate code
 * at compile time at following location.
 * /app/build/generated/ksp/debug/kotlin/com/hadiyarajesh/codegeneration/repository
 */
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class MyRepositoryModule {
//    @Binds
//    abstract fun bindMyRepository(impl: MyRepositoryImpl): MyRepository
//}
