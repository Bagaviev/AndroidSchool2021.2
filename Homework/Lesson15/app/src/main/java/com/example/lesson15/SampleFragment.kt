package com.example.lesson15

import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = SampleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewCounter.text = arguments?.getInt(FRAGM_CNT).toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGM_CNT = "FRAGM_CNT"

        fun newInstance(fragCnt: Int): SampleFragment {
            return SampleFragment().apply {
                arguments = bundleOf(FRAGM_CNT to fragCnt)
            }
        }
    }
}