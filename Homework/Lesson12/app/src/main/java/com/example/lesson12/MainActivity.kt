package com.example.lesson12

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

// вместо seekBar будет valueAnimator - стрелка будет крутиться сама
// цвет текста будет плавно меняться от зеленого до красного
// размер текста скорости будет увеличиваться в зависимости от самой скорости
// сам спидометр должен сохранять размер

class MainActivity : AppCompatActivity() {
    lateinit var speedometer: Speedometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer = findViewById(R.id.speedometer)

        animateSpeedometer(speedometer)
    }

    private fun animateSpeedometer(speedometer: Speedometer) {
        val speedSizeAnim = AnimatorInflater.loadAnimator(this, R.animator.speed_size_animator) as ValueAnimator
        val speedColorAnim = AnimatorInflater.loadAnimator(this, R.animator.speed_color_animator) as ValueAnimator

        speedSizeAnim.addUpdateListener {
                animator: ValueAnimator -> speedometer.speedTextPainter.textSize = (animator.animatedValue as Float)
                speedometer.invalidate()
        }

        speedColorAnim.addUpdateListener {
                animator: ValueAnimator -> speedometer.speedTextPainter.color = (animator.animatedValue as Int)
                speedometer.invalidate()
        }

        ValueAnimator.ofInt(0, Speedometer.maxSpeed)     // тут не через xml решил делать, тк хочу макс скорость атрибут подтягивать. Хз как это сделать из xml, поэтому из кода
            .apply {
                duration = 10000
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = DecelerateInterpolator()
                addUpdateListener { animator: ValueAnimator ->
                    val speed = animator.animatedValue as Int
                    Speedometer.currentSpeed = speed
                    speedometer.invalidate()
                }
            }.start()

        speedSizeAnim.start()
        speedColorAnim.start()
    }
}