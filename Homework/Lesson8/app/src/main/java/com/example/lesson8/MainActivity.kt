package com.example.lesson8

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.red


class MainActivity : AppCompatActivity() {
    lateinit var seekBar: SeekBar
    lateinit var speedometer: Speedometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer = findViewById(R.id.speedometer)

        seekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.max = Speedometer.maxSpeed
                speedometer.setSpeed(progress)

                if (Speedometer.currentSpeed <= Speedometer.maxSpeed / 3) {
                    speedometer.speedTextPainter.color = Speedometer.lowSpeedTextColor
                } else if (Speedometer.currentSpeed <= 2 * Speedometer.maxSpeed / 3) {
                    speedometer.speedTextPainter.color = Speedometer.medSpeedTextColor
                } else {
                    speedometer.speedTextPainter.color = Speedometer.hiSpeedTextColor
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    /*private fun resolveMaxSpeed() {      // вернуло 0 хз почему видимо не узнаем
        val typedValue = TypedValue()
        speedometer.context.theme.resolveAttribute(R.styleable.Speedometer_maxSpeed, typedValue, true)
        Log.e("Logs", "xml value: ${typedValue.float}")
    }*/
}