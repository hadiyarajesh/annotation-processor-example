package com.hadiyarajesh.codegeneration.repository

import android.util.Log
import com.hadiyarajesh.annotations.GenerateBinding
import javax.inject.Inject

interface MyRepository {
    fun getMessage(): String
}

@GenerateBinding(type = MyRepository::class)
class MyRepositoryImpl @Inject constructor() : MyRepository {
    override fun getMessage(): String {
        val className = this::class.simpleName!!
        val message = """
            Class <i>$className</i> is automatically registered as the implementation of 
            <i>${MyRepository::class.simpleName}</i> interface by the annotation processor.
        """.trimIndent()
        Log.d(className, message)
        return message
    }
}
