package com.example.lesson17.utils

import android.util.Log
import com.example.lesson17.pojo.User
import org.json.JSONException
import org.json.JSONObject

/**
 * @author Bulat Bagaviev
 * @created 30.10.2021
 */

class DataConverter {

    companion object {
        fun convertFromJson(data: String): User? {
            var user: User? = null
            try {
                var obj = JSONObject(data)
                var userId = obj.getInt("userId")
                var id = obj.getInt("id")
                var title = obj.getString("title")
                var completed = obj.getBoolean("completed")
                user = User(userId, id, title, completed)
            } catch (e: JSONException) {
                Log.e("TAG", "convertFromJson: $e.toString()")
            }
            return user
        }

        fun convertToJson(data: User): JSONObject? {
            var obj: JSONObject? = null
            try {
                obj = JSONObject()
                obj.put("userId", data.userId)
                obj.put("id", data.id)
                obj.put("title", data.title)
                obj.put("completed", data.completed)
            } catch (e: JSONException) {
                Log.e("TAG", "convertToJson: $e.toString()")
            }
            return obj
        }
    }
}