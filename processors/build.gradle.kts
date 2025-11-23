plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.hilt.core)
    implementation(libs.symbol.processing.api)

    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)

    implementation(project(":annotations"))
}
