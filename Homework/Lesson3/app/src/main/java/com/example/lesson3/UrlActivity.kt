package com.example.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UrlActivity : AppCompatActivity() {
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)
        textView = findViewById(R.id.textViewUri);

        val action = intent?.action
        val data = intent?.data

        if (Intent.ACTION_VIEW == action && data != null) {
            val param = data.getQueryParameter("param2");
            textView.append(" [${param?.uppercase()}]");
        }
    }

    override fun onNewIntent(intent: Intent?) {
        textView.append(" + 1");
        super.onNewIntent(intent)
    }
}