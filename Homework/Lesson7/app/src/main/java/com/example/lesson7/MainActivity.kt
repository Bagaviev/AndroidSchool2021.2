package com.example.lesson7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.adapters.MainAdapter
import com.example.lesson7.utils.Converter
import com.example.lesson7.utils.MyListener
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewMain: RecyclerView
    lateinit var adapterMain: MainAdapter
    var converter = Converter()

    companion object{
        lateinit var whatDataToUse: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewMain = findViewById(R.id.recyclerViewMain)

        adapterMain = MainAdapter(object: MyListener {
            override fun onItemClick(msg: String) {
                startActivity()
                whatDataToUse = msg
            }
        })

        recyclerViewMain.adapter = adapterMain
    }

    private fun startActivity() {
        var intent = Intent(applicationContext, CalculateActivity::class.java)   
        startActivity(intent)

    // TODO: 30.09.2021 1) Не успевал сделать через параметр Intent, сделал через свой флаг
    }
}