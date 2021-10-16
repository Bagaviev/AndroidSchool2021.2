package com.example.lesson12_2

/**
 * @author Bulat Bagaviev
 * @created 23.09.2021
 */

interface Element

data class ElementApple(val root: ElementBasket): Element

data class ElementBasket(val appleList: ArrayList<ElementApple>) : Element

data class ElementSummary(val value: Int = 0) : Element