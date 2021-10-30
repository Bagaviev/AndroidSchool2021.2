package com.example.lesson17.net

import com.example.lesson17.pojo.User

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

interface NetConnector {
    fun doGet(): User?

    fun doPost(): User?

    override fun toString(): String
}