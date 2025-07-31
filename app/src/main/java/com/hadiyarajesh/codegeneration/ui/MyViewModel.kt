package com.hadiyarajesh.codegeneration.ui

import androidx.lifecycle.ViewModel
import com.hadiyarajesh.codegeneration.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    fun doSomething() {
        repository.doSomething()
    }
}
