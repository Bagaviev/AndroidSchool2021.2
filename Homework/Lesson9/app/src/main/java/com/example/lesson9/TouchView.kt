package com.example.lesson9

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.lesson9.utils.Box
import com.example.lesson9.utils.DrawerType
import com.example.lesson9.utils.Line
import java.lang.IllegalStateException

/**
 * @author Bulat Bagaviev
 * @created 10.10.2021
 */

class TouchView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {    // еще раз закрепляем: тут мы получаем context в конструкторе нашей view на вход, далее передаем в конструктор родителя, тк без него он работать не может
    lateinit var currentBox: Box
    lateinit var currentLine: Line

    var boxesList = mutableListOf<Box>()
    var linesList = mutableListOf<Line>()

    val path = Path()

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        strokeWidth = 10f
        style = when (MainActivity.CURRENT_DRAWER) {
            DrawerType.CURVE, DrawerType.LINE -> Paint.Style.STROKE
            else -> Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {

            drawPath(
                path,
                paint
            )   // сначала и здесь when сделал, но напрасно, отрисовываем каждый раз все объекты

            for (i in boxesList) {
                var left = Math.min(i.origin!!.x, i.current.x)
                var right = Math.max(i.origin!!.x, i.current.x)

                var top = Math.min(i.origin!!.y, i.current.y)
                var bottom = Math.max(i.origin!!.y, i.current.y)
                drawRect(left, top, right, bottom, paint)
            }

            for (i in linesList) {
                drawLine(i.origin!!.x, i.origin!!.y, i.current.x, i.current.y, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action ?: throw IllegalStateException("null event")
        var point = PointF(event.x, event.y)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                when (MainActivity.CURRENT_DRAWER) {
                    DrawerType.CURVE -> path.moveTo(point.x, point.y)
                    DrawerType.BOX -> {
                        currentBox = Box(point)
                        boxesList.add(currentBox)
                    }
                    else -> {
                        currentLine = Line(point)
                        linesList.add(currentLine)
                    }
                }
                true
            }
            MotionEvent.ACTION_MOVE -> {
                when (MainActivity.CURRENT_DRAWER) {
                    DrawerType.CURVE -> {
                        path.lineTo(point.x, point.y)
                        invalidate()
                    }
                    DrawerType.BOX -> {
                        if (currentBox != null) {
                            currentBox.current = point!!
                            invalidate()
                        }
                    }
                    DrawerType.LINE -> {
                        if (currentLine != null) {
                            currentLine.current = point!!
                            invalidate()
                        }
                    }
                }
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    fun clearAll() {
        path.reset()
        boxesList.clear()
        linesList.clear()
        invalidate()
    }
}