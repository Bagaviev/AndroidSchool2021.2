package com.example.lesson26

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var startButton: Button
    lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG", "onCreate() called")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        startButton = findViewById(R.id.buttonStart)
        stopButton = findViewById(R.id.buttonStop)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        Log.d("TAG", "onStart() called")
        super.onStart()

        startButton.setOnClickListener { startOurService() }
        stopButton.setOnClickListener { stopOurService() }
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroy() called")
        super.onDestroy()
    }

    private fun startOurService() {
        Log.d("TAG", "start service comand")
        val intent = Intent(this, VisibleService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intent)
        }
    }

    private fun stopOurService() {
        Log.d("TAG", "stop service comand")
        val intent = Intent(this, VisibleService::class.java)

        applicationContext.stopService(intent)
    }
}