package com.hadiyarajesh.processors

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * Provides an instance of [GenerateBindingProcessor] to the Kotlin Symbol Processing (KSP) framework.
 */
class GenerateBindingProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return GenerateBindingProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}
