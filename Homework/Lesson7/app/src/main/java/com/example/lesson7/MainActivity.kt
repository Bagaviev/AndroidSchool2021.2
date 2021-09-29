package com.example.lesson7

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView1: RecyclerView
    lateinit var button: Button
    lateinit var adapter: StateAdapter

    companion object {
        var valueFromEditText: Double = 0.0
        var indexFromEditText: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView1 = findViewById(R.id.recyclerView1)
        button = findViewById(R.id.button)

        button.setOnClickListener{
            calculate(indexFromEditText)
        }

        adapter = StateAdapter()
        recyclerView1.adapter = adapter

    }

    private fun calculate(index: Int) {
        adapter.valuesState[index].value = valueFromEditText
        var valuesStateUpd = adapter.converter.convert(adapter.valuesState, index)

        adapter.valuesState = valuesStateUpd as ArrayList<ConverterValue>
        adapter.notifyDataSetChanged()
    }
}