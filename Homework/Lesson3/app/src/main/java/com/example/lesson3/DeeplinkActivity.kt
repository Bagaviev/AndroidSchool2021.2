package com.example.lesson3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeeplinkActivity : AppCompatActivity() {
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        textView = findViewById(R.id.textViewDeep);

        val action = intent?.action
        val data = intent?.data

        if (Intent.ACTION_VIEW == action && data != null) {
            val param = data.getQueryParameter("param1");
            textView.append(" [$param]");
        }
    }
}