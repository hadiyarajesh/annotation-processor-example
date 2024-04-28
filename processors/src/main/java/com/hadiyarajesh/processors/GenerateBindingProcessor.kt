package com.hadiyarajesh.processors

import com.google.auto.service.AutoService
import com.hadiyarajesh.annotations.GenerateBinding
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import dagger.Binds
import dagger.Module
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

@AutoService(Processor::class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
class GenerateBindingProcessor : AbstractProcessor() {
    private val generateBindingAnnotation = GenerateBinding::class

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(generateBindingAnnotation.qualifiedName!!)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
    }

    override fun process(type: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv
            .getElementsAnnotatedWith(generateBindingAnnotation.java)
            .filterIsInstance<TypeElement>()
            .forEach { typeElement: TypeElement ->
                generateHiltModuleWithBinding(typeElement)
            }

        return true
    }

    private fun generateHiltModuleWithBinding(type: TypeElement) {
        val firstInterface: TypeMirror = type.interfaces.firstOrNull() ?: type.superclass
        val boundType: TypeName = TypeName.get(firstInterface)
        val boundTypeName: Name = processingEnv.typeUtils.asElement(firstInterface).simpleName

        val methodSpec = MethodSpec.methodBuilder("bind$boundTypeName")
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addAnnotation(Binds::class.java)
            .addParameter(TypeName.get(type.asType()), "impl")
            .returns(boundType)

        val classSpec = TypeSpec.classBuilder("${boundTypeName}Module")
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addAnnotation(Module::class.java)
            .addAnnotation(
                AnnotationSpec.builder(ClassName.get("dagger.hilt", "InstallIn"))
                    .addMember(
                        "value",
                        "\$T.class",
                        ClassName.get("dagger.hilt.components", "SingletonComponent")
                    )
                    .build()
            )
            .addMethod(methodSpec.build())
            .addOriginatingElement(type)
            .build()

        val javaFile = JavaFile.builder(ClassName.get(type).packageName(), classSpec).build()
        javaFile.writeTo(processingEnv.filer)
    }
}
