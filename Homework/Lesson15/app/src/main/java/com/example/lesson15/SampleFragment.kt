package com.example.lesson15

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson15.databinding.SampleFragmentBinding

/**
 * @author Bulat Bagaviev
 * @created 22.10.2021
 */

class SampleFragment: Fragment() {
    private var _binding: SampleFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("frgm", "onAttach() ${this.javaClass} $number")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("frgm", "onCreate() ${this.javaClass} $number")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = SampleFragmentBinding.inflate(inflater, container, false)
        Log.e("frgm", "onCreateView() ${this.javaClass} $number")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewCounter.text = arguments?.getInt(FRAGM_CNT).toString()
        Log.e("frgm", "onViewCreated() ${this.javaClass} $number")
    }

    override fun onStart() {
        Log.e("frgm", "onStart() ${this.javaClass} $number")
        super.onStart()
    }

    override fun onResume() {
        Log.e("frgm", "onResume() ${this.javaClass} $number")
        super.onResume()
    }

    override fun onPause() {
        Log.e("frgm", "onPause() ${this.javaClass} $number")
        super.onPause()
    }

    override fun onStop() {
        Log.e("frgm", "onStop() ${this.javaClass} $number")
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("frgm", "onDestroyView() ${this.javaClass} $number")
        _binding = null
    }

    override fun onDestroy() {
        Log.e("frgm", "onDestroy() ${this.javaClass} $number")
        number--
        super.onDestroy()
    }


    override fun onDetach() {
        Log.e("frgm", "onDetach() ${this.javaClass} $number")
        super.onDetach()
    }

    companion object {
        private const val FRAGM_CNT = "FRAGM_CNT"
        private var number = 0

        fun newInstance(fragCnt: Int): SampleFragment {
            number++
            return SampleFragment().apply {
                arguments = bundleOf(FRAGM_CNT to fragCnt)
            }
        }
    }
}