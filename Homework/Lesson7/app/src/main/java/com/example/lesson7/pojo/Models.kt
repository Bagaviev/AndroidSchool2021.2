package com.example.lesson7

import java.io.Serializable

/**
 * @author Bulat Bagaviev
 * @created 29.09.2021
 */

data class ConverterUnit(val label: String, val toBaseRate: Double): Serializable {
    val fromBaseRate: Double = 1.0 / toBaseRate
}

data class ConverterValue(val converterUnit: ConverterUnit, var value: Double): Serializable

data class Quantity(val label: String, val converterUnits: List<ConverterUnit>): Serializable