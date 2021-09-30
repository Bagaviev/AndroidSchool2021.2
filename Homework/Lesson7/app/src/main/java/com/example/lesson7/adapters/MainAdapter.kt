package com.example.lesson7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.ConverterValue
import com.example.lesson7.Quantity
import com.example.lesson7.R
import com.example.lesson7.utils.Converter
import com.example.lesson7.utils.MyListener
import java.util.ArrayList

/**
 * @author Bulat Bagaviev
 * @created 30.09.2021
 */
class MainAdapter(private val listener: MyListener) : RecyclerView.Adapter<MyViewHolder>() {
    var converter = Converter()
    var items = ArrayList(converter.availableQuantities)

    companion object {
        var COUNTER_STATE = 0
        const val LENGTH = 0
        const val AREA = 1
    }

    init {
        for (i in 0..1)
            items.addAll(converter.availableQuantities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            LENGTH -> {
                LengthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_length, parent, false))
            }
            AREA -> {
                AreaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_area, parent, false))
            }
            else -> TODO("Я не знаю зачем это здесь, но так надо")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position].label) {
            "Длина" -> LENGTH
            "Площадь" -> AREA
            else -> TODO("Я не знаю зачем это здесь, но так надо")
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is LengthViewHolder -> {
                val item: Quantity = items[position]
                holder.textViewL.text = item.label

                holder.itemView.setOnClickListener {
                    listener.onItemClick("Length")
                }
            }

            is AreaViewHolder -> {
                val item: Quantity = items[position]
                holder.textViewA.text = item.label

                holder.itemView.setOnClickListener {
                    listener.onItemClick("Area")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class LengthViewHolder(itemView: View) : MyViewHolder(itemView) {
    val textViewL: TextView = itemView.findViewById(R.id.textViewLength)
}

class AreaViewHolder(itemView: View) : MyViewHolder(itemView) {
    val textViewA: TextView = itemView.findViewById(R.id.textViewArea)
}