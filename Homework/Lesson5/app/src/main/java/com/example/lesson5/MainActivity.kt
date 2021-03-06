package com.example.lesson5

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity() : AppCompatActivity() {

    companion object {
        const val DOTS_TO_WIN: Int = 3
        const val MAX_MOVE_CNT: Int = 9
        const val EMPTY_LEVEL: Int = 0
        const val CROSS_LEVEL: Int = 1
        const val CIRCLE_LEVEL: Int = 2
        const val WIN_MESSAGE = "You win!"
        const val LOSE_MESSAGE = "You lose, try again!"
        const val DRAW_MESSAGE = "It's a draw!"
        const val CLICK = "Stub"
        var MOVE_CNT: Int = 0
        var WIN_CNT: Int = 0
        var LOSE_CNT: Int = 0
        var STAR_FILLER_CNT: Int = 0
        var WINE_FILLER_CNT: Int = 0
    }

    private lateinit var buttonR: Button
    private lateinit var textViewMsg: TextView
    private lateinit var textViewPlayerScore: TextView
    private lateinit var textViewAiScore: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var imageViewPlayerWine: ImageView
    private lateinit var imageViewAiWine: ImageView

    private val imageViewsRefs = arrayOf(R.id.iv0, R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8)
    private val imageViewsHolder = Array(3) { arrayOfNulls<ImageView>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonR = findViewById(R.id.buttonRestart)
        textViewMsg = findViewById(R.id.textViewInfo)
        textViewPlayerScore = findViewById(R.id.textViewPlayerScore)
        textViewAiScore = findViewById(R.id.textViewAIScore)
        imageViewPlayerWine = findViewById(R.id.imageViewPlayerWine)
        imageViewAiWine = findViewById(R.id.imageViewAIWine)
        initMap()
    }

    override fun onStart() {
        super.onStart()
        buttonR.setOnClickListener { restartGame() }
    }

    private fun restartGame() {
        for (i in imageViewsHolder.indices) {
            for (j in imageViewsHolder[i].indices) {
                checkScoreWipe(WINE_FILLER_CNT)
                checkScoreWipe(STAR_FILLER_CNT)
                imageViewsHolder[i][j]?.setImageLevel(0)
                imageViewsHolder[i][j]?.setBackgroundColor(Color.parseColor("#00E6F5EA"))
                textViewMsg.text = ""
                textViewMsg.setBackgroundResource(R.color.textinfo_empty_background)
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

    private fun printMessage(str: String) {
        if (str == WIN_MESSAGE) textViewMsg.setBackgroundColor(
            Color.parseColor("#63ff85")
        ) else if (str == LOSE_MESSAGE) textViewMsg.setBackgroundColor(
            Color.parseColor("#ff7070")
        ) else textViewMsg.setBackgroundColor(Color.parseColor("#c953c0"))
        textViewMsg.text = str
    }

    private fun doPlayerMove(v: ImageView) {
        if (v?.drawable?.level == EMPTY_LEVEL) {
            initAudio(CLICK)

            v?.drawable?.level = CROSS_LEVEL
            MOVE_CNT++

            if (checkWin(CROSS_LEVEL)) {
                freezeMap()
                WIN_CNT++

                WINE_FILLER_CNT += 3333

                imageViewPlayerWine.setImageLevel(WINE_FILLER_CNT)
                updateScore(WIN_MESSAGE)

                initAudio(WIN_MESSAGE)

                printMessage(WIN_MESSAGE)
                colorize(CROSS_LEVEL)
                return
            }
            doAiMove()
        }
    }

    private fun checkScoreWipe(value: Int) {
        if (value >= 9999) {
            WINE_FILLER_CNT = 0
            STAR_FILLER_CNT = 0
            WIN_CNT = 0
            LOSE_CNT = 0
            textViewPlayerScore.setText("0")
            textViewAiScore.setText("0")
            imageViewPlayerWine.setImageLevel(0)
            imageViewAiWine.setImageLevel(0)
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
                printMessage(DRAW_MESSAGE)
                initAudio(DRAW_MESSAGE)
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
                    initAudio(LOSE_MESSAGE)

                    LOSE_CNT++

                    STAR_FILLER_CNT += 3333

                    imageViewAiWine.setImageLevel(STAR_FILLER_CNT)

                    updateScore(LOSE_MESSAGE)

                    printMessage(LOSE_MESSAGE)
                    colorize(CIRCLE_LEVEL)
                }
                break
            }
        }
    }

    private fun iterator(i: Int, j: Int) {
        if (j == 3) {
            for (k in 0 until j) imageViewsHolder[i][k]?.setBackgroundColor(Color.parseColor("#f5e3b0"))
        } else if (i == 3) {
            for (k in 0 until i) imageViewsHolder[k][j]?.setBackgroundColor(Color.parseColor("#f5e3b0"))
        } else if (i == j) {
            for (k in 0..i) imageViewsHolder[k][k]?.setBackgroundColor(Color.parseColor("#f5e3b0"))
        } else {
            var k = 0
            var m = j
            while (k <= j && m >= 0) {
                imageViewsHolder[k][m]?.setBackgroundColor(Color.parseColor("#f5e3b0"))
                k++
                m--
            }
        }
    }

    private fun colorize(sym: Int) {
        when (countWinStreak(sym)) {
            "h1" -> iterator(1, 3)
            "h0" -> iterator(0, 3)
            "h2" -> iterator(2, 3)
            "v0" -> iterator(3, 0)
            "v1" -> iterator(3, 1)
            "v2" -> iterator(3, 2)
            "d1" -> iterator(2, 2)
            "d2" -> iterator(0, 2)
        }
    }

    private fun countWinStreak(sym: Int): String? {
        var winStreak = String()
        var i = 0
        val j = i
        while (i < imageViewsHolder.size) {
            val horizontalCondition =
                (imageViewsHolder[i][j]?.drawable?.level == sym) and
                (imageViewsHolder[i][j + 1]?.drawable?.level == sym) and
                (imageViewsHolder[i][j + 2]?.drawable?.level == sym)
            val verticalCondition =
                (imageViewsHolder[j][i]?.drawable?.level == sym) and
                (imageViewsHolder[j + 1][i]?.drawable?.level == sym) and
                (imageViewsHolder[j + 2][i]?.drawable?.level == sym)
            val mainDiagCondition =
                (imageViewsHolder[j][j]?.drawable?.level == sym) and
                (imageViewsHolder[j + 1][j + 1]?.drawable?.level == sym) and
                (imageViewsHolder[j + 2][j + 2]?.drawable?.level == sym)
            val diagCondition =
                (imageViewsHolder[j][j + 2]?.drawable?.level == sym) and
                (imageViewsHolder[j + 1][j + 1]?.drawable?.level == sym) and
                (imageViewsHolder[j + 2][j]?.drawable?.level == sym)

            if (horizontalCondition && i == 0) winStreak = "h0"
            else if (horizontalCondition && i == 1) winStreak = "h1"
            else if (horizontalCondition && i == 2) winStreak = "h2"

            if (verticalCondition && i == 0) winStreak = "v0"
            else if (verticalCondition && i == 1) winStreak = "v1"
            else if (verticalCondition && i == 2) winStreak = "v2"

            if (mainDiagCondition) winStreak = "d1"
            else if (diagCondition) winStreak = "d2"
            i++
        }
        return winStreak
    }

    private fun initAudio(type: String) {
        mediaPlayer = when (type) {
            DRAW_MESSAGE -> MediaPlayer.create(this, R.raw.draw)
            WIN_MESSAGE -> MediaPlayer.create(this, R.raw.win)
            LOSE_MESSAGE -> MediaPlayer.create(this, R.raw.lose)
            else -> MediaPlayer.create(this, R.raw.click)
        }
        mediaPlayer.start()
    }

    private fun updateScore(type: String) {
        when (type) {
            WIN_MESSAGE -> textViewPlayerScore.text = WIN_CNT.toString()
            LOSE_MESSAGE -> textViewAiScore.text = LOSE_CNT.toString()
        }
    }
}