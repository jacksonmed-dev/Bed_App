package com.jacksonmed.bed.activities.overview.bed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.databinding.FragmentBedBinding
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.utils.Constants.Companion.SENSOR_URL
import okhttp3.Request
import okhttp3.Response


class BedFragment : Fragment() {
    private val viewModel: BedViewModel by activityViewModels()
    private var _binding: FragmentBedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBedBinding.inflate(inflater, container, false)
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

        binding.switchBedData.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                viewModel.getBedData()
                viewModel.bedDataBitmap.observe(viewLifecycleOwner, Observer { response ->
                    binding.imageViewBedData.setImageBitmap(response)
                })
            }
        }





//        var request: Request = Request.Builder().url(SENSOR_URL).build()
//        var okSse: OkSse = OkSse()
//        var sse: ServerSentEvent = okSse.newServerSentEvent(request, object: ServerSentEvent.Listener {
//            override fun onOpen(sse: ServerSentEvent?, response: Response?) {
//                Log.d("Open", "Connection Open")
//            }
//
//            override fun onMessage(
//                sse: ServerSentEvent?,
//                id: String?,
//                event: String?,
//                message: String?
//            ) {
//                Log.d("Open", message!!)
//            }
//
//            override fun onComment(sse: ServerSentEvent?, comment: String?) {
//                Log.d("Open", "Connection Open")
//            }
//
//            override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean {
//                Log.d("Open", "Connection Open")
//                return true
//            }
//
//            override fun onRetryError(
//                sse: ServerSentEvent?,
//                throwable: Throwable?,
//                response: Response?
//            ): Boolean {
//                Log.d("Open", "Connection Open")
//                return true
//            }
//
//            override fun onClosed(sse: ServerSentEvent?) {
//                Log.d("Open", "Connection Open")
//            }
//            // Change this function. I set the request to be null for demo purposes
//            override fun onPreRetry(sse: ServerSentEvent?, originalRequest: Request?): Request? {
//                var request: Request? = null
//                Log.d("Open", "Connection Open")
//                return request
//            }
//
//        })


    }

    companion object {
        fun newInstance() = BedFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}