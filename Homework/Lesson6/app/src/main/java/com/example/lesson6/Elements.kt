package com.example.lesson6

/**
 * @author Bulat Bagaviev
 * @created 23.09.2021
 */

interface Element

data class ElementBasket(val text: String, val appleList: List<ElementApple>) : Element

data class ElementApple(val text: String): Element

data class ElementSummary(val text: String, val value: Int) : Element