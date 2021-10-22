package com.example.lesson15

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson15.databinding.ActivityMainBinding

/*
Экран разделен на две части. В верхней находятся 2 кнопки с надписями "Добавить" и "Вычесть", radiobutton с пунктами "add" и "replace"
, checkbox с текстом "add to backstack"
В нижней части находится фрагмент с TextView с начальным значением 0. При нажатии на кнопку "Добавить" мы делаем транзакцию фрагмента
с выбранными параметрами и отображаем в textview количество фрагментов в стеке. При нажатии на кнопку "Вычесть" верхний фрагмент убирается.
0 - минимальное значение
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)      //      viewBinding для Activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        binding.buttonAdd.setOnClickListener { addFragment() }
        binding.buttonRemove.setOnClickListener { removeFragment() }
        super.onStart()
    }

    private fun removeFragment() {

    }

    private fun addFragment() {

    }
}