package com.example.lesson8

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import kotlin.coroutines.coroutineContext
import kotlin.math.min

/**
 * @author Bulat Bagaviev
 * @created 05.10.2021
 */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Speedometer(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {    // бага была - падает с малоинф ошибкой если указаны defStyleAttrs: Int, defStyleRes: Int

    companion object {
        val SMALL_BORDER_MARGIN = 8f
        val ARROW_WIDTH = 2f
        val STROKE_WIDTH = 8f

        val placehoderList = mutableListOf<String>()
        var currentSpeed: Int = 0
        var maxSpeed: Int = 0

        var lineColor: Int = 0
        var lowSpeedTextColor: Int = 0
        var medSpeedTextColor: Int = 0
        var hiSpeedTextColor: Int = 0
        var mainColor: Int = Color.parseColor("#906aad")
    }

    val cSpeedRect = Rect()

    val mainArcRect = RectF(0f, 0f, 400f, 400f)

    val smallArcRect = RectF(150f, 150f, 250f, 250f)

    val additionalTextPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 25f }

    val speedTextPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 35f }

    val arrowPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply { strokeWidth = STROKE_WIDTH }

    val mainArcPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply {    // удобно, подсказка параметров появляется при наборе в пустоту
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH * 2
    }

    var smalArcPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

    init {
        repeat(6) { placehoderList.add(String()) }      // text0, text1, text2, text3, text4, textCs

        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.Speedometer,
            R.attr.speedometerDefaultAttr,
            R.style.SpeedometerDefaultStyle
        )

        try {
            currentSpeed = typedArray.getInt(R.styleable.Speedometer_speed, 0)
            maxSpeed = typedArray.getInt(R.styleable.Speedometer_maxSpeed, 0)
            lineColor = typedArray.getColor(R.styleable.Speedometer_lineColor, 0)
            lowSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_lowSpeedTextColor, 0)
            medSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_medSpeedTextColor, 0)
            hiSpeedTextColor = typedArray.getColor(R.styleable.Speedometer_hiSpeedTextColor, 0)
        } finally {
            typedArray.recycle()
        }

        mainArcPainter.color = mainColor
        smalArcPainter.color = mainColor
        arrowPainter.color = lineColor
        speedTextPainter.color = lowSpeedTextColor
    }

    fun setSpeed(newSpeed: Int) {
        currentSpeed = newSpeed
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {      // drawLine(200f, 200f, 270f, 30f, linePainter)     // полярные координаты не отрабатывали как нужно
        canvas?.apply {
            translate(SMALL_BORDER_MARGIN, SMALL_BORDER_MARGIN)
            drawArc(mainArcRect, 180f, 180f, true, mainArcPainter)
            drawArc(mainArcRect, 180f / maxSpeed * currentSpeed + 180f, ARROW_WIDTH, true, arrowPainter)
            drawArc(smallArcRect, 180f, 180f, true, smalArcPainter)

            initText()
        }
    }

    private fun Canvas.initText() {     // extension
        setSpeedPlaceholders(currentSpeed, maxSpeed)

        drawText(placehoderList[0], 20f, 185f, additionalTextPainter)
        drawText(placehoderList[1], 70f, 90f, additionalTextPainter)
        drawText(placehoderList[2], 180f, 40f, additionalTextPainter)
        drawText(placehoderList[3], 290f, 90f, additionalTextPainter)
        drawText(placehoderList[4], 340f, 185f, additionalTextPainter)

        val s = "$currentSpeed km/h"
        speedTextPainter.getTextBounds(s, 0, s.length, cSpeedRect)
        drawText(s, 200f - cSpeedRect.centerX(), 100f - cSpeedRect.centerY(), speedTextPainter)
    }

    fun setSpeedPlaceholders(speed: Int, maxSpeed: Int) {       // четенько получилось с первого раза
        placehoderList[0] = "0"
        placehoderList[4] = maxSpeed.toString()
        placehoderList[5] = speed.toString()

        var maxSpeedTmp = maxSpeed

        for (i in 3 downTo 0) {
            maxSpeedTmp -= maxSpeed / 4
            placehoderList[i] = maxSpeedTmp.toString()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {      // 200dp x 200dp etalon
// чето не адекватно отработало, потом разберемся
//        val size = min(w, h) - STROKE_WIDTH
//        mainArcRect.set(
//            paddingLeft.toFloat(),
//            paddingTop.toFloat(),
//            size - paddingRight,
//            size - paddingBottom
//        )
    }
}