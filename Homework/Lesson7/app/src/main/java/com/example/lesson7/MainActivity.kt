package com.example.lesson7

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.adapters.StateAdapter

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
        if (index >= 0 ) {              // если честно хз почему adapterPosition может возвращать -1, но выяснять не охота
            adapter.valuesState[index].value = valueFromEditText
            var valuesStateUpd = adapter.converter.convert(adapter.valuesState, index)

            adapter.valuesState = valuesStateUpd as ArrayList<ConverterValue>
            adapter.notifyDataSetChanged()
        }
    }
}