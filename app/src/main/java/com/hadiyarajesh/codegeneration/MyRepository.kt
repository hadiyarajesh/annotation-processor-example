package com.hadiyarajesh.codegeneration

import android.util.Log
import com.hadiyarajesh.annotations.GenerateBinding
import java.util.UUID
import javax.inject.Inject

interface MyRepository {
    fun doSomething()
}

@GenerateBinding(type = MyRepository::class)
class MyRepositoryImpl @Inject constructor() : MyRepository {
    override fun doSomething() {
        Log.d("MyRepositoryImpl", "doSomething from ${this::class.simpleName}")
    }
}
