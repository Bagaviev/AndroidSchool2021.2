package com.example.lesson13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

/**
 * @author Bulat Bagaviev
 * @created 20.10.2021
 */

class SecondFragment: Fragment() {
    lateinit var button: Button
    lateinit var publicApi: PublicApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        publicApi = activity as PublicApi
        button = view.findViewById(R.id.button)
        button.setOnClickListener { publicApi.onClick() }
    }

}