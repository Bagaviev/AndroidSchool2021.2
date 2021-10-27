package com.example.lesson16.fragments

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson16.databinding.FragmentTimerBinding
import java.util.concurrent.TimeUnit

/**
 * @author Bulat Bagaviev
 * @created 26.10.2021
 */

class TimerFragment : Fragment() {
    private var backHandler: Handler
    private var uiHandler = Handler(Looper.getMainLooper())

    private val task = Runnable { calculateTime() }
    private var mTimerTime: Int = 6

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val RPT_CNT = "RPT_CNT"

        fun newInstance(rptCnt: Int): TimerFragment {
            return TimerFragment().apply {
                arguments = bundleOf(RPT_CNT to rptCnt)
            }
        }
    }

    init {
        val backgroundThread = HandlerThread("background")
        backgroundThread.start()
        backHandler = Handler(backgroundThread.looper)
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
        backHandler.post(task)
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        backHandler.removeCallbacksAndMessages(null)
        uiHandler.removeCallbacksAndMessages(null)
    }

    private fun calculateTime() {
        if (mTimerTime > 0) {
            mTimerTime--
            uiHandler.post(this::updateTime)
            backHandler.postDelayed(task, TimeUnit.SECONDS.toMillis(1))      // жесткая бага если сделать TimeUnit.SECONDS.toSeconds(1)
        } else {
            Looper.myLooper()?.quitSafely()
        }
    }

    private fun updateTime() {
        binding.textViewCnt.text = mTimerTime.toString()
    }
}