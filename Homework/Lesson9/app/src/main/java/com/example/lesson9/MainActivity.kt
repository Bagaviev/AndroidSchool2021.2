package com.example.lesson9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var buttonClear: Button
    lateinit var touchView: TouchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClear = findViewById(R.id.buttonClear)
        touchView = findViewById(R.id.touchView)

        buttonClear.setOnClickListener {
            touchView.path.reset()
            touchView.invalidate()
        }
    }
}