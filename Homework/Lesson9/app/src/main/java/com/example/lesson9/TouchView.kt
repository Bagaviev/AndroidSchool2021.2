package com.example.lesson9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.IllegalStateException

/**
 * @author Bulat Bagaviev
 * @created 10.10.2021
 */
class TouchView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {                  // еще раз закрепляем: тут мы получаем context в конструкторе нашей view на вход, далее передаем в конструктор родителя, тк без него он работать не может
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    val path = Path()

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action ?: throw IllegalStateException("null event")
        var x = event.x
        var y = event.y

        Log.d("Touch", "x = $x, y = $y")        // Log.d("Touch", "event = CANCEL")

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                invalidate()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }


}