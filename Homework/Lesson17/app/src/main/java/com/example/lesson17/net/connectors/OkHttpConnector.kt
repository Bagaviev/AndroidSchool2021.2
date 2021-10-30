package com.example.lesson17.net.connectors

import com.example.lesson17.net.NetConnector

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

class OkHttpConnector: NetConnector {
    override fun doGet(): String {
        return "stub"
    }

    override fun doPost(): String {
        return "stub"
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}