package com.example.lesson16.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.lesson16.PublicApi
import com.example.lesson16.R
import com.example.lesson16.databinding.FragmentInitBinding


/**
 * @author Bulat Bagaviev
 * @created 26.10.2021
 */

class InitFragment: Fragment() {
    private var _binding: FragmentInitBinding? = null
    private val binding get() = _binding!!

    lateinit var publicApi: PublicApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInitBinding.inflate(inflater, container, false)
        publicApi = activity as PublicApi
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonRun.setOnClickListener { publicApi.onClick() }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getText(): String {         // классно придумал, классно реализовали для нас
        return if (binding.editTextCnt.text.toString().isDigitsOnly() and binding.editTextCnt.text.toString().isNotEmpty()) binding.editTextCnt.text.toString()
               else "0"
    }
}