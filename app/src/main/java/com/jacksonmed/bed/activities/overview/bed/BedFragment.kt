package com.jacksonmed.bed.activities.overview.bed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jacksonmed.bed.activities.main.MainViewModelFactory
import com.jacksonmed.bed.databinding.BedFragmentBinding
import com.jacksonmed.bed.repository.RepositoryBed

class BedFragment : Fragment() {
    private lateinit var viewModel: BedViewModel
    private var _binding: BedFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BedFragmentBinding.inflate(inflater, container, false)

        binding.switchMassage.setOnCheckedChangeListener {_ , isChecked ->
            val repository = RepositoryBed()
            val viewModelFactory = BedViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(BedViewModel::class.java)

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



        val root = binding.root
        return root
    }

    companion object {
        fun newInstance() = BedFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}