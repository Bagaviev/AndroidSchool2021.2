package com.example.lesson16.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson16.databinding.FragmentTimerBinding

/**
 * @author Bulat Bagaviev
 * @created 26.10.2021
 */

class TimerFragment: Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewCnt.text = arguments?.getInt(RPT_CNT).toString()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}