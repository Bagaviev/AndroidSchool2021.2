package com.example.lesson6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = MyAdapter(object: MyListener {
            override fun onOurCustomClick(text: String) {
                showText(text)
            }
        })

        val elementsList: List<Element> = listOf(
            ElementBasket("1", emptyList()),
            ElementApple("2.1"),
            ElementApple("2.2"),
            ElementSummary("3", 4))

        adapter.items = elementsList

        recyclerView.adapter = adapter
    }

//        adapter.notifyDataSetChanged()

    fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    interface MyListener {
        fun onOurCustomClick(text: String)
    }
}