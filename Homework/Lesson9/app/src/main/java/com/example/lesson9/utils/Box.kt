package com.example.lesson9.utils

import android.graphics.PointF

/**
 * @author Bulat Bagaviev
 * @created 10.10.2021
 */

data class Box(val origin: PointF?) {
    var current: PointF = PointF(0f, 0f)
}
