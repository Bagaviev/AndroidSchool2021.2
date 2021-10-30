package com.example.lesson17.net

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

interface NetConnector {
    fun doGet(): String

    fun doPost(): String

    override fun toString(): String
}