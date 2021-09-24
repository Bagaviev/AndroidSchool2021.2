package com.example.lesson6

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdapter
    lateinit var addBasketButton: Button
    lateinit var removeBasketsButton: Button

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        addBasketButton = findViewById(R.id.buttonAddBasket)
        removeBasketsButton = findViewById(R.id.buttonClearBaskets)

        addBasketButton.setOnClickListener {
            adapter.items.add(adapter.items.size - 1, ElementBasket(arrayListOf()))
            adapter.updateList()
        }

        removeBasketsButton.setOnClickListener {
            adapter.items.removeAll { it is ElementApple }      // а вот так красиво на Java конечно бы не получилось
            adapter.items.removeAll { it is ElementBasket }     // заработало как надо с первого раза, фантастика
            adapter.updateList()
        }

        adapter = MyAdapter(object: MyListener {
            override fun onOurCustomClick(text: String) {
                showText(text)
            }
        })

        adapter.items = arrayListOf(ElementSummary())
        recyclerView.adapter = adapter

        // свой красивый разделитель делаем

        // свой красивый разделитель делаем
        val itemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider)!!)
        recyclerView.addItemDecoration(itemDecoration)
    }

    fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}