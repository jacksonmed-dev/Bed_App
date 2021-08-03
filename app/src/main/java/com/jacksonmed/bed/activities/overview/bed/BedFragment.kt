package com.jacksonmed.bed.activities.overview.bed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.databinding.BedFragmentBinding
import com.jacksonmed.bed.model.StatusResponse


class BedFragment : Fragment() {
    private val viewModel: BedViewModel by activityViewModels()
    private var _binding: BedFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchMassage.setOnCheckedChangeListener {_ , isChecked ->
            if(isChecked) {
                viewModel.startMassage().observe(viewLifecycleOwner, object: Observer<ApiResponse<StatusResponse>> {
                    override fun onChanged(apiResponse: ApiResponse<StatusResponse>) {
                        if (apiResponse.error != null && apiResponse.response == null) {   // Call unsuccessful
                            Toast.makeText(context, apiResponse.error.toString(), Toast.LENGTH_LONG).show()
                            return
                        }
                        binding.textViewResponse.text = apiResponse.response?.body()?.toString()
                    }

                })
            }else {
                viewModel.stopMassage().observe(viewLifecycleOwner, object: Observer<ApiResponse<StatusResponse>> {
                    override fun onChanged(apiResponse: ApiResponse<StatusResponse>) {
                        if (apiResponse.error != null && apiResponse.response == null) {   // Call unsuccessful
                            Toast.makeText(context, apiResponse.error.toString(), Toast.LENGTH_LONG).show()
                            return
                        }
                        binding.textViewResponse.text = apiResponse.response?.body()?.toString()
                    }
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