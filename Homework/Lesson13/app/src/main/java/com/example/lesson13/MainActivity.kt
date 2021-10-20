package com.example.lesson13

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), PublicApi {
    var thirdFragment: ThirdFragment? = null
    var firstFragment: FirstFragment? = null
    var root: ViewGroup? = null
    var SCREEN_ORIENT: Int = 1      // 1 portrait, 2 landscape

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root = findViewById(R.id.root_view)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        SCREEN_ORIENT = resources.configuration.orientation
        super.onConfigurationChanged(newConfig)
    }

    override fun onClick() {
        if (thirdFragment == null) {

            firstFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as FirstFragment
            val newFragmentContainer = FragmentContainerView(this)

            newFragmentContainer.layoutParams = when (SCREEN_ORIENT) {
                1 -> ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 650)       // метод не отработает для ConstraintLayout там он заполнит, все что есть пространство. Или же нужны доп параметры.
                else -> ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250)    // если вообще закомментить задание LayoutParams, то будет вставляться снизу сжатым фрагмент
            }                                                                                      // сначала делал так, без weight, но тогда при альбомной ориентации вставлялся очень высокий фрагмент, если хардкодить height dp, поэтому weight юзаем, и вставляем не в свободное простр-во, а заполняем экран 2-мя фрагментами, а затем тут через addView() снизу докидываем фрагмент. Теперь после этого мы можем задать высоту 3-го фрагмета тут через хардкод, а не MATCH_PARENT, и он будет корректно снизу отрисовываться, а не целиком на весь экран.

            newFragmentContainer.id = R.id.third_fragment_id            // также сделал weight для layout'a - 1, чтобы равных размеров все было и вставлялось

            root?.addView(newFragmentContainer)                         //
            thirdFragment = ThirdFragment.newInstance(getText())        // делегируем

            supportFragmentManager.beginTransaction()
                .add(R.id.third_fragment_id, thirdFragment!!)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    override fun setText(text: String) {
        thirdFragment?.setText(text)
    }

    override fun getText(): String {
        return firstFragment?.getText().toString()
    }
}