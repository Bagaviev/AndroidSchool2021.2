package com.example.lesson7.utils

import com.example.lesson7.ConverterUnit
import com.example.lesson7.ConverterValue
import com.example.lesson7.Quantity

/**
 * @author Bulat Bagaviev
 * @created 29.09.2021
 */
class Converter {
    val availableQuantities: List<Quantity> = listOf(
        Quantity(
            "Площадь",
            listOf(
                ConverterUnit("Кв. метр", 1.0),
                ConverterUnit("Кв. километр", 1000000.0),
                ConverterUnit("Гектар", 10000.0),
                ConverterUnit("Кв. сантиметр", 0.0001),
            )
        ),
        Quantity(
            "Длина",
            listOf(
                ConverterUnit("Метр", 1.0),
                ConverterUnit("Километр", 1000.0),
                ConverterUnit("Миля", 1609.34),
                ConverterUnit("Сантиметр", 0.01),
                ConverterUnit("Милиметр", 0.001),
                ConverterUnit("Микрометр", 0.000001),
            )
        )
    )

    fun convert(converterValues: List<ConverterValue>, index: Int = 0): List<ConverterValue> {
        val mutableValues = converterValues.toMutableList()
        val baseValue = mutableValues[index]
        for (i in mutableValues.indices) {
            mutableValues[i] = mutableValues[i].copy(
                value = (baseValue.value * baseValue.converterUnit.toBaseRate * mutableValues[i].converterUnit.fromBaseRate)
            )
        }
        return mutableValues
    }

    fun initList(quantity: Quantity): List<ConverterValue> =
        convert(quantity.converterUnits.map { ConverterValue(it, 100.0) })
}