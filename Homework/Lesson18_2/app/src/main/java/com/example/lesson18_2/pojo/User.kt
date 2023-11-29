package com.example.lesson18_2.pojo

/**
 * @author Bulat Bagaviev
 * @created 30.10.2021
 */

 data class User(var userId: Int, var id: Int, var title: String, var completed: Boolean)

open class User2(var userId: Int, var id: Int, var title: String, var completed: Boolean)

abstract class User3(var userId: Int, var id: Int, var title: String, var completed: Boolean)