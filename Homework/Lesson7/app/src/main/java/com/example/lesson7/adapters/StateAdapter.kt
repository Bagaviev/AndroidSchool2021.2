package com.example.lesson7.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.MainActivity
import com.example.lesson7.R
import com.example.lesson7.utils.Converter

class StateAdapter() : RecyclerView.Adapter<StateAdapter.ViewHolder>() {

    // on focus listener
    // переместить элемент на первое место
    // adapter? notify move

    val nums = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.')
    var converter = Converter()
    var valuesState = ArrayList(converter.initList(converter.availableQuantities[0]))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state = valuesState[position]

        holder.textView.text = state.converterUnit.label
        holder.editText.setText(state.value.toBigDecimal().toPlainString())

        holder.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotBlank() and checkEditableIsInArray(nums, s)) {
                    MainActivity.indexFromEditText = holder.adapterPosition
                    MainActivity.valueFromEditText = holder.editText.text.toString().toDouble()
                } else {
                    holder.editText.post { Toast.makeText(holder.editText.context, "Nan", Toast.LENGTH_SHORT).show() }
                    MainActivity.indexFromEditText = 0
                    MainActivity.valueFromEditText = 0.0
                }
            }
        })

        holder.editText.setOnFocusChangeListener { v, hasFocus ->
            if (holder.adapterPosition >= 0) {
                var swap = valuesState.removeAt(holder.adapterPosition)
                valuesState.add(0, swap)

                notifyItemMoved(holder.adapterPosition, 0)  // notifyDatasetChanged вылетал почемуто, а тут все завелось
                MainActivity.indexFromEditText = 0
//                Log.e("Logs", holder.adapterPosition.toString() + " edittext focus got")
            }
        }
    }

    fun checkEditableIsInArray(alphabet: CharArray, s: Editable): Boolean {
        for (symbol in s)
            if (symbol !in alphabet)
                return false
        return true
    }

    override fun getItemCount(): Int {
        return valuesState.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView1)
        val editText: EditText = view.findViewById(R.id.editTextText1)
    }
}