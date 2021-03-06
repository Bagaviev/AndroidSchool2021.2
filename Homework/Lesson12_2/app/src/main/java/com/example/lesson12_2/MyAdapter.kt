package com.example.lesson12_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// в целом тут 2 кейса рассматривается - когда несколько типов клик методов для каждой view на recycler item
// и несколько типов items со своими клик методами в одном recyclerView

class MyAdapter(private val listener: MyListener): RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        var COUNTER_STATE = 0
        const val BASKET = 0
        const val APPLE = 1
        const val SUMMARY = 2
    }

    lateinit var items: ArrayList<Element>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            BASKET -> BasketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false))
            APPLE -> AppleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_apple, parent, false))
            SUMMARY -> SummaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_summary, parent, false))
            else -> TODO("Я не знаю зачем это здесь, но так надо")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is ElementBasket -> BASKET
            is ElementApple -> APPLE
            is ElementSummary -> SUMMARY
            else -> TODO("Я не знаю зачем это здесь, но так надо")
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is BasketViewHolder -> {
                val element = items[position] as ElementBasket
                holder.button.setOnClickListener {

                    if (!isBasketFull(element)) {
                        element.appleList.add(ElementApple(element))        // сохраняем ссылку на себя же, чтобы потом заюзать при удалении яблока - подчистить список по ссылке
                        items.add(position + 1, ElementApple(element))

                        updateList()
                    } else
                        listener.onOurCustomClick("Больше 3-х нельзя")
                }
            }

            is AppleViewHolder -> {
                val element = items[position] as ElementApple
                holder.button.setOnClickListener {
                    items.removeAt(position)
                    element.root.appleList.remove(element)

                    listener.onOurCustomClick("Яблоко удалено")
                    updateList()
                }
            }

            is SummaryViewHolder -> {
                val element = items[position] as ElementSummary
                holder.textViewCnt.text = COUNTER_STATE.toString()
            }
        }

        /*
            holder.textView.text = items[position]
            holder.button.setOnClickListener {
                listener.onButtonCLick(holder.button.text.toString())   // // для клика по определенной view внутри item'a
            }

            holder.setOnClickListenerCustom(listener)
            holder.itemView.setOnClickListener{     // для клика по всей view, нам же нужно по кнопке внутри нее обработать
                listener.onButtonCLick(items[position])
            }
        */
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun isBasketFull(elementBasket: ElementBasket): Boolean {
        return elementBasket.appleList.size >= 3
    }

    fun calculateCounter() {
        COUNTER_STATE = items.filterIsInstance<ElementBasket>()      // тоже красиво конечно
                             .sumOf{ it.appleList.size }             // и заработало как надо с первого раза, фантастика
    }

    fun updateList() {
        calculateCounter()
        notifyDataSetChanged()
    }
}

open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class BasketViewHolder(itemView: View) : MyViewHolder(itemView) {    // такая конструкция эквивалентна super(itemView)
    val textView: TextView = itemView.findViewById(R.id.textViewBasket)
    val button: Button = itemView.findViewById(R.id.buttonBasket)
}

class AppleViewHolder(itemView: View) : MyViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textViewApple)
    val button: Button = itemView.findViewById(R.id.buttonApple)

    /*fun setOnViewClickListener(listenerCustom: OnViewClickListener) {     // заглушка как раз для обработки нескольких кликов по разным view внутри одного элемента
        button.setOnClickListener { listenerCustom.onAppleButtonClick() }
    }*/
}

class SummaryViewHolder(itemView: View) : MyViewHolder(itemView) {
    val textViewInfo: TextView = itemView.findViewById(R.id.textViewSummaryInfo)
    val textViewCnt: TextView = itemView.findViewById(R.id.textViewSummaryCnt)
}
