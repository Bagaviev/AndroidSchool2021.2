package com.example.lesson7

/**
 * @author Bulat Bagaviev
 * @created 29.09.2021
 */

data class ConverterValue(val converterUnit: ConverterUnit, var value: Double)

data class ConverterUnit(val label: String, val toBaseRate: Double) {
    val fromBaseRate: Double = 1.0 / toBaseRate
}

data class Quantity(val label: String, val converterUnits: List<ConverterUnit>)