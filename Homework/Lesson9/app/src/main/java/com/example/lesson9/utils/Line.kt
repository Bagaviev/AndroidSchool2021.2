package com.example.lesson9.utils

import android.graphics.PointF

/**
 * @author Bulat Bagaviev
 * @created 11.10.2021
 */

data class Line(val origin: PointF?, var color: Int) {
    var current: PointF = PointF(0f, 0f)
}
