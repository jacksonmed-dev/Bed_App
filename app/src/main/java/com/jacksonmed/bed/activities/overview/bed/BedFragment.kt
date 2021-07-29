package com.jacksonmed.bed.activities.overview.bed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacksonmed.bed.databinding.BedFragmentBinding


class BedFragment : Fragment() {
    private val viewModel: BedViewModel by activityViewModels()
    private var _binding: BedFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BedFragmentBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchMassage.setOnCheckedChangeListener {_ , isChecked ->
            if(isChecked) {
                viewModel.startMassage()
                viewModel.startMassageResponse.observe(viewLifecycleOwner, Observer { response ->
                    binding.textViewResponse.text = response.body()?.toString()
                })
            }else {
                viewModel.stopMassage()
                viewModel.stopMassageResponse.observe(viewLifecycleOwner, Observer { response ->
                    binding.textViewResponse.text = response.body()?.toString()
                })
            }
        }

        binding.buttonBedStatus.setOnClickListener {
            viewModel.getBedStatus()
        }
    }

    companion object {
        fun newInstance() = BedFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}