package com.example.lesson5

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity() : AppCompatActivity() {

//        Log.e("log:", "imageButtonHolder[1][1].id $i")
//        Toast.makeText(this,"bla", Toast.LENGTH_SHORT).show()
//        val i: Int? = imageButtonHolder[1][1]?.drawable?.level

    companion object {
        const val DOTS_TO_WIN: Int = 3
        const val MAX_MOVE_CNT: Int = 9
        const val EMPTY_LEVEL: Int = 0
        const val CROSS_LEVEL: Int = 1
        const val CIRCLE_LEVEL: Int = 2
        var MOVE_CNT: Int = 0
        var WIN_CNT: Int = 0
        var LOSE_CNT: Int = 0
        var DRAW_CNT: Int = 0
    }

    private val imageViewsRefs = arrayOf(R.id.iv0, R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8)
    private val imageViewsHolder = Array(3) { arrayOfNulls<ImageView>(3) }

    private lateinit var buttonR: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonR = findViewById<Button>(R.id.buttonRestart)
        initMap()
    }

    override fun onStart() {
        super.onStart()
        buttonR.setOnClickListener { restartGame() }
    }

    private fun restartGame() {
        for (i in imageViewsHolder.indices) {
            for (j in imageViewsHolder[i].indices) {
                imageViewsHolder[i][j]?.setImageLevel(0)
//                imageButtonHolder[i][j]?.setBackgroundColor(Color.parseColor("#bd9bd1"))
                MOVE_CNT = 0
                initMap()
            }
        }
    }

    private fun initMap() {
        var i = 0
        var s = 0
        while (i < imageViewsHolder.size) {
            for (j in imageViewsHolder[i].indices) {
                imageViewsHolder[i][j] = findViewById(imageViewsRefs[s])
                imageViewsHolder[i][j]?.setOnClickListener {
                    doPlayerMove(it as ImageView)
                }
                s++
            }
            i++
        }
    }

    private fun freezeMap() {
        for (i in imageViewsHolder.indices) {
            for (element in imageViewsHolder[i]) {
                element?.setOnClickListener(null)
            }
        }
    }


    private fun doPlayerMove(v: ImageView) {
        if (v?.drawable?.level == EMPTY_LEVEL) {
            v?.drawable?.level = CROSS_LEVEL
            MOVE_CNT++
            if (checkWin(CROSS_LEVEL)) {
                freezeMap()
                WIN_CNT++
//                updateScore(WIN_MESSAGE)
//                printMessage(WIN_MESSAGE)
//                colorize(PLAYER_SYM)
                return
            }
            doAiMove()
        }
    }

    private fun checkWin(symb: Int): Boolean {
        var winToD1 = 0
        var winToD2 = 0
        for (i in imageViewsHolder.indices) {
            var winToCol = 0
            var winToRow = 0
            for (j in imageViewsHolder[i].indices) {
                if (imageViewsHolder[i][j]?.drawable?.level == symb) winToRow++
                if (imageViewsHolder[j][i]?.drawable?.level  == symb) winToCol++
            }
            if (winToRow == DOTS_TO_WIN || winToCol == DOTS_TO_WIN) return true
            if (imageViewsHolder[i][i]?.drawable?.level == symb) winToD1++
            if (imageViewsHolder[i][imageViewsHolder[i].size - i - 1]?.drawable?.level == symb) winToD2++
        }
        return winToD1 == DOTS_TO_WIN || winToD2 == DOTS_TO_WIN
    }

    private fun doAiMove() {
        val random = Random()
        var x: Int
        var y: Int
        while (true) {
            if (MOVE_CNT >= MAX_MOVE_CNT) {
//                printMessage(com.example.reclinetask.GameActivity.DRAW_MESSAGE)
                DRAW_CNT++
//                updateScore(com.example.reclinetask.GameActivity.DRAW_MESSAGE)
                freezeMap()
                return
            }
            x = random.nextInt(imageViewsHolder.size)
            y = random.nextInt(imageViewsHolder.size)
            if (imageViewsHolder[x][y]?.drawable?.level == EMPTY_LEVEL) {
                imageViewsHolder[x][y]?.drawable?.level = CIRCLE_LEVEL
                MOVE_CNT++
                if (checkWin(CIRCLE_LEVEL)) {
                    freezeMap()
                    LOSE_CNT++
//                    updateScore(com.example.reclinetask.GameActivity.LOSE_MESSAGE)
//                    printMessage(com.example.reclinetask.GameActivity.LOSE_MESSAGE)
//                    colorize(com.example.reclinetask.GameActivity.AI_SYM)
                }
                break
            }
        }
    }
}