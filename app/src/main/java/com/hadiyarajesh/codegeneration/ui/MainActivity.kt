package com.hadiyarajesh.codegeneration.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.hadiyarajesh.codegeneration.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = myViewModel.getMessage()
        findViewById<TextView>(R.id.message).text =
            HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}
