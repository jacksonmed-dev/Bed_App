package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacksonmed.bed.utils.bluetooth.service.MyBluetoothService
import com.jacksonmed.bed.activities.overview.bed.inflatable.BedDrawableView
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.databinding.FragmentBedBinding
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants.Companion.BLUETOOTH_ADDRESS
import com.jacksonmed.bed.utils.bluetooth.BluetoothHandler
import com.jacksonmed.bed.utils.bluetooth.BluetoothViewModel
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.generateBluetoothByteArray


class BedFragment : Fragment() {
    private val isBluetooth = true
    private val viewModel: BedViewModel by activityViewModels()
    private val bluetoothViewModel: BluetoothViewModel by activityViewModels()
    private var _binding: FragmentBedBinding? = null

    private lateinit var bedDrawableView: BedDrawableView

    private lateinit var bluetoothService: MyBluetoothService
    private lateinit var mContext: Context

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBedBinding.inflate(inflater, container, false)

        bedDrawableView = BedDrawableView(mContext)
        bluetoothService = MyBluetoothService(BluetoothHandler(bluetoothViewModel.bluetoothResponse), BLUETOOTH_ADDRESS, mContext)
        bluetoothService.connect()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bedDrawableView.createRectangles(20)

        bluetoothViewModel.bluetoothResponse.observe(viewLifecycleOwner, object: Observer<BluetoothResponse<String>> {
            override fun onChanged(t: BluetoothResponse<String>?) {
                binding.textViewResponse.text = t?.response
            }
        })

        binding.switchMassage.setOnCheckedChangeListener {_ , isChecked ->
            if(isChecked) {
                if(isBluetooth) bluetoothService.sendMessage(generateBluetoothByteArray("1"))
                else {
                    viewModel.startMassage().observe(
                        viewLifecycleOwner,
                        object : Observer<ApiResponse<StatusResponse>> {
                            override fun onChanged(apiResponse: ApiResponse<StatusResponse>) {
                                if (apiResponse.error != null && apiResponse.response == null) {   // Call unsuccessful
                                    Toast.makeText(
                                        context,
                                        apiResponse.error.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return
                                }
                                binding.textViewResponse.text =
                                    apiResponse.response?.body()?.toString()
                            }

                        })
                }
            }else {
                if(isBluetooth) bluetoothService.sendMessage(generateBluetoothByteArray("0"))
                else {
                    viewModel.stopMassage().observe(
                        viewLifecycleOwner,
                        object : Observer<ApiResponse<StatusResponse>> {
                            override fun onChanged(apiResponse: ApiResponse<StatusResponse>) {
                                if (apiResponse.error != null && apiResponse.response == null) {   // Call unsuccessful
                                    Toast.makeText(
                                        context,
                                        apiResponse.error.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return
                                }
                                binding.textViewResponse.text =
                                    apiResponse.response?.body()?.toString()
                            }
                        })
                }
            }
        }

        binding.buttonBedStatus.setOnClickListener {
            val data = "#".toByteArray()
            if(isBluetooth) bluetoothService.sendMessage(data)
            else viewModel.getBedStatus()
        }

        binding.switchBedData.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                viewModel.getBedData()
                viewModel.bedDataBitmap.observe(viewLifecycleOwner, Observer { response ->
                    binding.imageViewBedData.setImageBitmap(response)
                })
            }
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