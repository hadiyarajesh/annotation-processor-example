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
    implementation(libs.javapoet)
    implementation("com.google.dagger:dagger:2.50")
    compileOnly(libs.google.auto.service)
    kapt(libs.google.auto.service)

    compileOnly(libs.incap.core)
    kapt(libs.incap.processor)
}
