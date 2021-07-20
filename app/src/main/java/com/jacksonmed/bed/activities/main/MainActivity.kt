package com.jacksonmed.bed.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.databinding.ActivityMainBinding
import com.jacksonmed.bed.activities.overview.SystemOverview
import com.jacksonmed.bed.repository.Repository

//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.jacksonmed.bed.R


//const val PI_URL = "http://192.168.86.122:5000/"
//var bedState: Boolean = false

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textViewResponse.text = "Hello World"


        binding.buttonLoadPatient.setOnClickListener { v ->
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getPatientInfo()
            viewModel.patientResponse.observe(this, Observer { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else {
                    binding.textViewResponse.text = response.errorBody().toString()
                }

            })
        }


        binding.buttonMassageStart.setOnClickListener { v ->
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.startMassage()
            viewModel.massageStartResponse.observe(this, Observer { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonMassageStop.setOnClickListener { v ->
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.stopMassage()
            viewModel.massageStopResponse.observe(this, Observer { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonBedStatus.setOnClickListener { v ->
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getBedStatus()
            viewModel.bedStatusResponse.observe(this, Observer { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonOverviewActivity.setOnClickListener { v ->
            val intent = Intent(this, SystemOverview::class.java)
            startActivity(intent)
        }
    }


//    private fun toggleBedSwitch(did: Int, state: Int){
//        val url = "https://api.coolebed.com/bed-api/device/fanPower?did=$did&state=$state"
//        val queue = Volley.newRequestQueue(this)
//        val stringRequest = object: StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                textView_response.text = "Bed State $state"
//            },
//            Response.ErrorListener { textView_response.text = "Error switching bed state" })
//        {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//
//        queue.add(stringRequest)
//    }
//
//    private fun relayTest(){
//        val queue = Volley.newRequestQueue(this)
//        val stringRequest = object: StringRequest(Request.Method.GET, PI_URL,
//            Response.Listener<String> { response ->
//                textView_response.text = "Its working"
//            },
//            Response.ErrorListener { textView_response.text = "Error toggling relays" })
//        {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//        queue.add(stringRequest)
//    }

}