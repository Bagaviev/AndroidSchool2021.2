package com.example.lesson5

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    private val imageButtonsRefs = arrayOf(R.id.iv0, R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8)
    private val imageButtonHolder = Array(3) {
        arrayOfNulls<ImageView>(3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        button.setBackgroundResource(R.drawable.cross);
//        Log.e("log:", "view № $i $j inflated")
//        Toast.makeText(this,"bla", Toast.LENGTH_SHORT).show()

        initMap()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initMap() {
        var i = 0
        var s = 0
        while (i < imageButtonHolder.size) {
            for (j in imageButtonHolder[i].indices) {
                imageButtonHolder[i][j] = findViewById(imageButtonsRefs[s])
                imageButtonHolder[i][j]?.setOnClickListener {
                    (it as ImageView).setImageLevel(1)
                }
                s++
            }
            i++
        }
    }

    // imageButton statelist обращение проверку состояния сделать

}