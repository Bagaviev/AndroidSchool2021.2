package com.example.lesson13

import android.net.wifi.aware.PublishConfig
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

/**
 * @author Bulat Bagaviev
 * @created 20.10.2021
 */

class FirstFragment: Fragment() {
    lateinit var editText: EditText
    lateinit var publicApi: PublicApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        publicApi = activity as PublicApi
        editText = view.findViewById(R.id.editText)
        editText.addTextChangedListener { publicApi.setText(it.toString()) }
    }

    fun getText(): Editable = editText.text
}