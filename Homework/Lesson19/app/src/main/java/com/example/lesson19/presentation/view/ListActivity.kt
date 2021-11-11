package com.example.lesson19.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson19.data.network.NetworkOkHttpImpl
import com.example.lesson19.databinding.ActivityListBinding
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val rawQuery = NetworkOkHttpImpl(OkHttpClient(), Json)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        binding.button.setOnClickListener { doGet() }
        super.onStart()
    }

    private fun doGet() {
        Thread {
            var result = rawQuery.getWeather().daily
            runOnUiThread { binding.textView.text = result.toString()}
        }.start()
    }
}