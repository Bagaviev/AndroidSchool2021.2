package com.example.lesson9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import com.example.lesson9.utils.DrawerType

class MainActivity : AppCompatActivity() {
    lateinit var buttonClear: Button
    lateinit var touchView: TouchView
    lateinit var drawerChooser: Spinner

    companion object {
        var CURRENT_DRAWER: DrawerType = DrawerType.CURVE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClear = findViewById(R.id.buttonClear)
        touchView = findViewById(R.id.touchView)
        drawerChooser = findViewById(R.id.spinner)

        buttonClear.setOnClickListener { touchView.clearAll() }

        drawerChooser.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                CURRENT_DRAWER = when (position) {
                    0 -> DrawerType.CURVE
                    1 -> DrawerType.BOX
                    else -> DrawerType.LINE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}