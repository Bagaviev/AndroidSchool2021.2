package com.example.lesson8

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

/**
 * @author Bulat Bagaviev
 * @created 05.10.2021
 */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Speedometer(context: Context, attributeSet: AttributeSet?, defStyleAttrs: Int, defStyleRes: Int) :
      View(context, attributeSet, defStyleAttrs, defStyleRes) {

    var speed: Int = 0
    var maxSpeed: Int = 200

    @ColorInt
    var lineColor: Int = Color.GRAY
    @ColorInt
    var lowSpeedTextColor: Int = Color.GREEN
    @ColorInt
    var medSpeedTextColor: Int = Color.YELLOW
    @ColorInt
    var hiSpeedTextColor: Int = Color.RED

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.Speedometer,
            R.attr.speedometerDefaultAttr,
            R.style.SpeedometerDefaultStyle
        )

        try {
            speed = typedArray.getInt(R.styleable.Speedometer_speed, 0)
            maxSpeed = typedArray.getInt(R.styleable.Speedometer_maxSpeed, 180)

            lineColor = typedArray.getColor(R.styleable.Speedometer_lineColor, Color.GRAY)
            lowSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_lowSpeedTextColor, Color.GREEN)
            medSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_medSpeedTextColor, Color.YELLOW)
            hiSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_hiSpeedTextColor, Color.RED)
        } finally {
            typedArray.recycle()
        }
    }
}