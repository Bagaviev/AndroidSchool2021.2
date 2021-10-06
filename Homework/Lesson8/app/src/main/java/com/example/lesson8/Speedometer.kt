package com.example.lesson8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
class Speedometer(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {    // бага была - падает с малоинф ошибкой если указаны defStyleAttrs: Int, defStyleRes: Int
    private var speed: Int
    private var maxSpeed: Int

    @ColorInt
    private var lineColor: Int
    @ColorInt
    private var lowSpeedTextColor: Int
    @ColorInt
    private var medSpeedTextColor: Int
    @ColorInt
    private var hiSpeedTextColor: Int

    val textPainter = Paint(Paint.ANTI_ALIAS_FLAG)
    val linePainter = Paint(Paint.ANTI_ALIAS_FLAG).apply { strokeWidth = 8f }

    var mainArc = Paint(Paint.ANTI_ALIAS_FLAG).apply {    // удобно, подсказка параметров появляется при наборе в пустоту
        style = Paint.Style.STROKE
        strokeWidth = 16f
    }

    var smalArc = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.Speedometer,
            0,      // R.attr.speedometerDefaultAttr,
            0       // R.style.SpeedometerDefaultStyle
        )

        try {
            speed = typedArray.getInt(R.styleable.Speedometer_speed, 0)
            maxSpeed = typedArray.getInt(R.styleable.Speedometer_maxSpeed, 0)

            lineColor = typedArray.getColor(R.styleable.Speedometer_lineColor, Color.GRAY)
            lowSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_lowSpeedTextColor, Color.GREEN)
            medSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_medSpeedTextColor, Color.YELLOW)
            hiSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_hiSpeedTextColor, Color.RED)
        } finally {
            typedArray.recycle()
        }

        mainArc.color = lineColor
        linePainter.color = lineColor
        smalArc.color = lineColor
        // тут конфигим Paint потом
    }

    fun setSpeed(newSpeed: Int) {
        speed = newSpeed
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {      // drawLine(200f, 200f, 270f, 30f, linePainter)     // полярные координаты не отрабатывали как нужно
        canvas?.apply {
            translate(8f, 8f)
            drawArc(0f, 0f, 400f, 400f, 180f, 180f, true, mainArc)
            drawArc(0f, 0f, 400f, 400f, 180f / maxSpeed * speed + 180f, 2f, true, linePainter)
            drawArc(150f, 150f, 250f, 250f, 180f, 180f, true, smalArc)
        }
    }
}