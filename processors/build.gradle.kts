plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":annotations"))
    implementation(libs.dagger)

    implementation(libs.javapoet) {
        because("A Java API for generating .java source files")
    }

    compileOnly(libs.google.auto.service) {
        because("A configuration/metadata generator for java.util.ServiceLoader-style service providers")
    }
    kapt(libs.google.auto.service)

    compileOnly(libs.incap.core) {
        because("Helper library and annotation processor for building incremental annotation processors")
    }
    kapt(libs.incap.processor)
}
