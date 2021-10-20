package com.example.lesson13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 * @author Bulat Bagaviev
 * @created 20.10.2021
 */

class ThirdFragment: Fragment() {
    lateinit var textView: TextView
    lateinit var publicApi: PublicApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.third_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        publicApi = activity as PublicApi
        textView = view.findViewById(R.id.textView)
        val data = arguments?.getString(TEXT_KEY)
        setText(data)
    }

    fun setText(text: String?) {
        textView.text = text
    }

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"

        fun newInstance(text: String): ThirdFragment {
            return ThirdFragment().apply {
                arguments = bundleOf(TEXT_KEY to text)
            }
        }
    }
}