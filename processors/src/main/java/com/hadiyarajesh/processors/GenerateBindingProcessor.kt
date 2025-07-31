package com.hadiyarajesh.processors

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.hadiyarajesh.annotations.GenerateBinding
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A KSP processor that generates Dagger-Hilt `@Module` classes with `@Binds` functions
 * for annotated implementation classes using `@GenerateBinding(type = ...)`.
 *
 * This helps reduce boilerplate in setting up Hilt bindings.
 *
 * Example:
 * ```
 * @GenerateBinding(type = MyRepository::class)
 * class MyRepositoryImpl : MyRepository
 * ```
 * This generates:
 * ```
 * @Module
 * @InstallIn(SingletonComponent::class)
 * abstract class MyRepositoryModule {
 *     @Binds
 *     abstract fun bindMyRepository(impl: MyRepositoryImpl): MyRepository
 * }
 * ```
 */
class GenerateBindingProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    private val annotationClass = GenerateBinding::class

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Find all classes annotated with @GenerateBinding
        val symbols = resolver
            .getSymbolsWithAnnotation(annotationClass.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()

        // Process each class to generate a Hilt module
        for (classDeclaration in symbols) {
            logger.info("Generating Hilt module for: ${classDeclaration.qualifiedName}")
            generateModule(classDeclaration)
        }

        return emptyList()
    }

    /**
     * Generates a Dagger-Hilt module with a `@Binds` method that binds
     * the implementation class to the interface specified in the annotation.
     */
    private fun generateModule(
        implClass: KSClassDeclaration
    ) {
        // Extract the @GenerateBinding annotation
        val annotation = implClass.annotations
            .first { it.shortName.asString() == annotationClass.simpleName }

        // Get the 'type' parameter from the annotation (the interface to bind)
        val typeArg = annotation.arguments
            .first { it.name?.asString() == "type" }.value as KSType

        val interfaceName = typeArg.declaration.simpleName.asString()
        val implClassName = implClass.simpleName.asString()
        val packageName = implClass.packageName.asString()
        val moduleName = "${interfaceName}Module"

        // Create the @Binds function
        val bindFunction = FunSpec.builder("bind$interfaceName")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(Binds::class)
            .addParameter("impl", implClass.toClassName())
            .returns(typeArg.toTypeName())
            .build()

        // Create the Dagger module class
        val moduleClass = TypeSpec.classBuilder(moduleName)
            .addKdoc("Hilt module for binding [$interfaceName] to [$implClassName].")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(Module::class)
            .addAnnotation(
                AnnotationSpec.builder(InstallIn::class)
                    .addMember("value = [%T::class]", SingletonComponent::class)
                    .build()
            )
            .addFunction(bindFunction)
            .build()

        // Write the file to generated sources
        val file = FileSpec.builder(packageName, moduleName)
            .addType(moduleClass)
            .build()

        file.writeTo(codeGenerator, Dependencies(false, implClass.containingFile!!))
    }
}
