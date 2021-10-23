package com.example.lesson15

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
    private var fragmentManager: FragmentManager = supportFragmentManager

    private var BACKSTACK_FLAG: Boolean = false
    private var ACTION_TYPE: Int = 0
    private var BACKSTACK_CNT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)      //      viewBinding для Activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        binding.checkBox.setOnCheckedChangeListener { _, isChecked -> BACKSTACK_FLAG = isChecked }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedID ->
            when (checkedID) {
                0, 1 -> ACTION_TYPE = checkedID
                else -> Unit
            }
        }

        fragmentManager.addOnBackStackChangedListener { updateCnt() }
        binding.buttonAdd.setOnClickListener { addFragment() }

        binding.buttonRemove.setOnClickListener { removeFragment() }
        updateCnt()

        super.onStart()
    }

    private fun removeFragment() {
        fragmentManager.popBackStack()
    }

    private fun addFragment() {
        when (ACTION_TYPE) {
            0 -> {
                if (BACKSTACK_FLAG) {
                    fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView, SampleFragment.newInstance(BACKSTACK_CNT))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("Our manager Backstack")
                        .commit()
                } else {
                    fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView, SampleFragment.newInstance(BACKSTACK_CNT))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            1 -> {
                if (BACKSTACK_FLAG) {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, SampleFragment.newInstance(BACKSTACK_CNT))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("Our manager Backstack")
                        .commit()
                } else {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, SampleFragment.newInstance(BACKSTACK_CNT))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            else -> Unit
        }
    }

    private fun updateCnt() {
        BACKSTACK_CNT = fragmentManager.backStackEntryCount + 1      // сначала брал fragments.size, но это некорректно было + сеттил сразу в xml фрагмент, потом перестал.
    }
}