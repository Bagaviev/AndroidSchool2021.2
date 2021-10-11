package com.example.lesson9

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.lesson9.utils.Box
import com.example.lesson9.utils.DrawerType
import com.example.lesson9.utils.Line

/**
 * @author Bulat Bagaviev
 * @created 10.10.2021
 */

class TouchView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {    // еще раз закрепляем: тут мы получаем context в конструкторе нашей view на вход, далее передаем в конструктор родителя, тк без него он работать не может
    lateinit var currentBox: Box
    lateinit var currentLine: Line
    var path: Path = Path()

    companion object {
        var CURRENT_DRAWER: DrawerType = DrawerType.LINE
        var CURRENT_COLOR: Int = Color.BLACK
    }

    var boxesList = mutableListOf<Box>()
    var linesList = mutableListOf<Line>()

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = CURRENT_COLOR
        strokeWidth = 10f
        style = when (CURRENT_DRAWER) {
            DrawerType.CURVE, DrawerType.LINE -> Paint.Style.STROKE
            else -> Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {

            drawPath(path, paint.apply { color = CURRENT_COLOR })   // тут не знаю как сделать сохранение цвета, объект сам продолжает свою линию

            for (i in boxesList) {
                var prevColorTmp = paint.color
                paint.color = i.color

                var left = Math.min(i.origin!!.x, i.current.x)
                var right = Math.max(i.origin.x, i.current.x)

                var top = Math.min(i.origin.y, i.current.y)
                var bottom = Math.max(i.origin.y, i.current.y)

                drawRect(left, top, right, bottom, paint)
                paint.color = prevColorTmp
            }

            for (i in linesList) {
                var prevColorTmp = paint.color
                paint.color = i.color

                drawLine(i.origin!!.x, i.origin.y, i.current.x, i.current.y, paint)
                paint.color = prevColorTmp
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action ?: throw IllegalStateException("null event")
        var point = PointF(event.x, event.y)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                when (CURRENT_DRAWER) {
                    DrawerType.CURVE -> path.moveTo(point.x, point.y)
                    DrawerType.BOX -> {
                        currentBox = Box(point, CURRENT_COLOR)
                        boxesList.add(currentBox)
                    }
                    else -> {
                        currentLine = Line(point, CURRENT_COLOR)
                        linesList.add(currentLine)
                    }
                }
                true
            }
            MotionEvent.ACTION_MOVE -> {
                when (CURRENT_DRAWER) {
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