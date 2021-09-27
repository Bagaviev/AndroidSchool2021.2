package com.example.activitylifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// {} нужны для произвольного выражения, а тут для переменной достаточно $
// через companion object this всегда будет companion MainActivity. Так что некорректно.

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    var className: String? = javaClass.simpleName

    private fun log(postfix: String) {
        Log.e("Logs", "Activity $className $postfix")
    }

    private fun startIntent() {
        var intentNew = Intent(this, OtherActivity::class.java)
        startActivity(intentNew)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.buttonStart)
        button.setOnClickListener { startIntent() }
        log("onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }


    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }
}