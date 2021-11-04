package com.example.lesson18_1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson18_1.databinding.FragmentTimerBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author Bulat Bagaviev
 * @created 26.10.2021
 */

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private var mTimerTime: Int = 0
    private lateinit var disposable: Disposable

    /*
    private var observer = object: Observer<Long> {
        override fun onSubscribe(d: Disposable) { disposable = d }

        override fun onNext(t: Long) {
            Log.d("TAG", "onNext: $t")
            updateTime(t)
        }

        override fun onError(e: Throwable) { Log.e("TAG", "onError: $e.toString()") }

        override fun onComplete() { Log.d("TAG", "onComplete: ") }
    }
*/  // более громоздкий приемник

    companion object {
        private const val RPT_CNT = "RPT_CNT"

        fun newInstance(rptCnt: Int): TimerFragment {
            return TimerFragment().apply {
                arguments = bundleOf(RPT_CNT to rptCnt)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTimerTime = arguments?.getInt(RPT_CNT)!!
        binding.textViewCnt.text = mTimerTime.toString()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        calculateTime()
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        disposable.dispose()
    }

    private fun calculateTime() {                       // наш источник
        disposable = Observable.intervalRange(1, mTimerTime.toLong(), 0, 1, TimeUnit.SECONDS)
            .map { mTimerTime - it }                    // удобный метод заменяет counter и sleep
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .doOnEach { updateTime(it.value!!) }      // Т.е можно проталкивать из источника метод по обновлению UI. Тогда observer' у остается лишь логировать и радоваться
//            .subscribe(observer)                      // Т.е если тут делать через анон объект то тут же и updateTime можно протолкнуть и все
            .subscribe { item -> updateTime(item) }
    }

    private fun updateTime(t: Long) {
        binding.textViewCnt.text = t.toString()
    }
}