package com.example.lesson18_2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import com.example.lesson18_2.net.ConnectorTypes
import com.example.lesson18_2.net.NetworkRepository
import com.example.lesson18_2.pojo.User
import com.example.lesson18_2.pojo.User2
import com.example.lesson18_2.pojo.User3
import kotlinx.coroutines.*

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
        buttonGet.setOnClickListener { lifecycleScope.launch { doGet2() } }     // тут немного магии понадобилось, чтобы заработал вызов, задать корутинный контекст
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


    private suspend fun doGet2() = coroutineScope {    // круто, работает!
        val user = async(Dispatchers.IO) {
//            Log.d("TAG", "\"Корутина выполняется на потоке: ${Thread.currentThread().name}\"")     // DefaultDispatcher-worker-1
//            delay(3000)
            networkRepository.currentConnector.doGet()
        }

        var t = Any()

        withContext(Dispatchers.Main) {
            val result = user.await()                 // в общем это то, чего мне не хватало в Executors get()...
            textViewData.text = result.toString()     // кажется мы заменили механизм с очередью событий (коллбеки) на что-то другое асинхронное
        }
    }

    fun doPost() {                            // по старинке
        Thread {                                      // обновляем запись о юзере, его описание и возвращаем новое
            var user = networkRepository.currentConnector.doPost()
            runOnUiThread { textViewData.text = user.toString() }
        }.start()

        isChangingConfigurations
        isFinishing
        test2().str
        test2("abc")
        // test3("str").str = "32"
        // test4()
    }

    data class test2(val str: String = "ded") : User2(2,2,"5",true) {
        fun getSmth(): String {
            return str
        }
    }

    class test22(str: String)

    fun String.addSome(str: String): String {
        return this + str
    }

    class test3(val str: String)
    class test4(var str: String) {
        var param: String = ""
            get() = field + "de"
            set(value) {
                field = value + "2"
            }
    }

    object Test1 {
        fun getSmth() = "uwu"
        val trf: MutableList<String> = mutableListOf("ed", "ed2")

        val tmp = trf
    }

}