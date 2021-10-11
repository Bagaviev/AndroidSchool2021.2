package com.example.lesson9

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson9.utils.DrawerType


class MainActivity : AppCompatActivity() {
    lateinit var buttonClear: Button
    lateinit var touchView: TouchView
    lateinit var drawerChooser: Spinner
    var palette = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClear = findViewById(R.id.buttonClear)
        touchView = findViewById(R.id.touchView)
        drawerChooser = findViewById(R.id.spinner)

        buttonClear.setOnClickListener { touchView.clearAll() }

        initPalette()

        for (i in palette) {
            i.setOnClickListener {
                var color: ColorDrawable? = i.background as ColorDrawable?      // спасибо stackOverFlow в очередной раз...
                TouchView.CURRENT_COLOR = color!!.color
            }
        }

        drawerChooser.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TouchView.CURRENT_DRAWER = when (position) {
                    0 -> DrawerType.LINE
                    1 -> DrawerType.BOX
                    else -> DrawerType.CURVE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initPalette() {
        palette.add(findViewById(R.id.iv0))
        palette.add(findViewById(R.id.iv1))
        palette.add(findViewById(R.id.iv2))
        palette.add(findViewById(R.id.iv3))
        palette.add(findViewById(R.id.iv4))
    }
}