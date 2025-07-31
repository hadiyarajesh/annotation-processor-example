package com.hadiyarajesh.codegeneration.repository

import android.util.Log
import com.hadiyarajesh.annotations.GenerateBinding
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
