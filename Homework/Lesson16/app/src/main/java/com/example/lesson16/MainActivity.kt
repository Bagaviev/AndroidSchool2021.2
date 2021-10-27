package com.example.lesson16

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.lesson16.databinding.ActivityMainBinding
import com.example.lesson16.fragments.InitFragment
import com.example.lesson16.fragments.TimerFragment

// В общем есть 3 способа low-level background взаимодействия
    // Через Handler и runnable (здесь решил так сделать, чтобы понять взаимодействие)
    // Через Handler и message callback (мой старый пример)
    // Через более упрощенный ScheduledExecutorService - тут ни handler, ни runnable, ни message не нужны.

class MainActivity : AppCompatActivity(), PublicApi {
    private lateinit var binding: ActivityMainBinding

    var initFragment: InitFragment? = null
    var timerFragment: TimerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onClick() {
        initFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as InitFragment
        timerFragment = TimerFragment.newInstance(getText().toInt())

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, timerFragment!!)
            .addToBackStack("Counter fragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun getText(): String {
        return initFragment?.getText().toString()
    }
}