package com.example.lesson17.net.connectors

import com.example.lesson17.net.NetConnector
import com.example.lesson17.pojo.User

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

class OkHttpConnector: NetConnector {
    override fun doGet(): User? {
        return User(1,1,"1", true)
    }

    override fun doPost(): User? {
        return User(1,1,"1", true)
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}