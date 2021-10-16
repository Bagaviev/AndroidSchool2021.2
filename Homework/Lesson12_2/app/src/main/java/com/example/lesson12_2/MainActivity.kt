package com.example.lesson12_2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
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
        val itemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider)!!)
        recyclerView.addItemDecoration(itemDecoration)

        // сеттим swipe + drag&drop аниматор списка
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {   // частично можно было через getSwipeDirs
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = when (viewHolder.itemViewType) {
                    MyAdapter.APPLE -> ItemTouchHelper.RIGHT
                    MyAdapter.BASKET -> ItemTouchHelper.LEFT
                    else -> 0
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                var positionTmp = viewHolder.adapterPosition        // вот тут было жестко - adapterPosition возвращает -1 NO_POSITION если был удален, бугурт

                when (viewHolder.itemViewType) {       // можно еще было через adapter.items[viewHolder.adapterPosition]
                    MyAdapter.APPLE -> {
                        var apple: ElementApple = adapter.items.removeAt(positionTmp) as ElementApple
                        adapter.notifyItemRemoved(positionTmp)
                        apple.root.appleList.remove(apple)

                        adapter.updateList()
                    }
                    else -> {
                        var basket: ElementBasket = adapter.items.removeAt(positionTmp) as ElementBasket
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)

                        repeat(basket.appleList.size) { adapter.items.removeAt(positionTmp) }
                        adapter.notifyItemRangeRemoved(positionTmp, basket.appleList.size)

                        basket.appleList.clear()
                        adapter.updateList()
                    }
                }
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                val fromPos = viewHolder.adapterPosition
//                val toPos = target.adapterPosition
//                Collections.swap(adapter.items, fromPos, toPos);
//                adapter.notifyItemMoved(fromPos, toPos);
//                showText("Перенесено")
                return true // true if moved, false otherwise
            }
        }

    private fun deleteBucket() {

    }

    private fun deleteApple() {

    }
}