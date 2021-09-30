package com.example.lesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.adapters.CalculateAdapter
import com.example.lesson7.utils.Converter

class CalculateActivity : AppCompatActivity() {
    lateinit var recyclerViewCalc: RecyclerView
    lateinit var buttonCalc: Button
    var adapterCalc = CalculateAdapter()
    var converter = Converter()

    companion object {
        var valueFromEditText: Double = 0.0
        var indexFromEditText: Int = 0
    }

    init {
        adapterCalc.valuesState.clear()
        if (MainActivity.whatDataToUse.equals("Length"))
            adapterCalc.valuesState.addAll(converter.initList(converter.availableQuantities[1]))
        else
            adapterCalc.valuesState.addAll(converter.initList(converter.availableQuantities[0]))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        recyclerViewCalc = findViewById(R.id.recyclerViewCalculate)
        buttonCalc = findViewById(R.id.buttonCalculate)

        buttonCalc.setOnClickListener{
            calculate(indexFromEditText)
        }

        recyclerViewCalc.adapter = adapterCalc

    }

    private fun calculate(index: Int) {
        if (index >= 0 ) {              // если честно хз почему adapterPosition может возвращать -1, но выяснять не охота
            adapterCalc.valuesState[index].value = valueFromEditText
            var valuesStateUpd = adapterCalc.converter.convert(adapterCalc.valuesState, index)

            adapterCalc.valuesState = valuesStateUpd as ArrayList<ConverterValue>
            adapterCalc.notifyDataSetChanged()
        }
    }
}