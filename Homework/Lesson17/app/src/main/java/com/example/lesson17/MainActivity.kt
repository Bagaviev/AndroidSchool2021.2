package com.example.lesson17

import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.lesson17.net.ConnectorTypes
import com.example.lesson17.net.NetworkRepository

// Для httpUrlConnection руками соберем json
// Для okHttp - Moshi.
// Kotlinx serialization потом в след раз опробуем.

class MainActivity : AppCompatActivity() {
    lateinit var buttonGet: Button
    lateinit var buttonPost: Button
    lateinit var textViewData: TextView
    lateinit var textViewType: TextView
    lateinit var switchMethod: SwitchCompat
    var networkRepository: NetworkRepository = NetworkRepository.getInstanse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGet = findViewById(R.id.buttonGet)
        buttonPost = findViewById(R.id.buttonPost)
        textViewData = findViewById(R.id.textViewData)
        textViewType = findViewById(R.id.textViewType)
        switchMethod = findViewById(R.id.switchMethon)

        textViewType.text = networkRepository.currentConnector.toString()
    }

    override fun onStart() {
        buttonGet.setOnClickListener { doGet() }
        buttonPost.setOnClickListener { doPost() }
        switchMethod.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked)
                networkRepository.setupConnector(ConnectorTypes.OK_HTTP)
            else
                networkRepository.setupConnector(ConnectorTypes.HTTP_URL_CONNECTION)

            textViewType.text = networkRepository.currentConnector.toString()
        }
        super.onStart()
    }

    private fun doGet() {       // просто выводим данные о юзере
        Thread {
            var user = networkRepository.currentConnector.doGet()
            runOnUiThread { textViewData.text = user.toString() }
        }.start()               // более красивый механизм тредирования не работал так как нужно.
    }

    private fun doPost() {      // обновляем запись о юзере, его описание и возвращаем новое
        Thread {
            var user = networkRepository.currentConnector.doPost()
            runOnUiThread { textViewData.text = user.toString() }
        }.start()
    }
}