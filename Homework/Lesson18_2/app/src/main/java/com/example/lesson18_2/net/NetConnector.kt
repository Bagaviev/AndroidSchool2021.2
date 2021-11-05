package com.example.lesson18_2.net

import com.example.lesson18_2.pojo.User

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

interface NetConnector {
    fun doGet(): User?

    fun doPost(): User?

    override fun toString(): String
}