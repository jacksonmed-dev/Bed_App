package com.jacksonmed.bed.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.databinding.ActivityMainBinding
import com.jacksonmed.bed.activities.overview.SystemOverview
import com.jacksonmed.bed.repository.Repository


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLoadPatient.setOnClickListener {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getPatientInfo()
            viewModel.patientResponse.observe(this,  { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else {
                    binding.textViewResponse.text = response.errorBody().toString()
                }

            })
        }


        binding.buttonMassageStart.setOnClickListener {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.startMassage()
            viewModel.massageStartResponse.observe(this,  { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonMassageStop.setOnClickListener {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.stopMassage()
            viewModel.massageStopResponse.observe(this,  { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonBedStatus.setOnClickListener {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getBedStatus()
            viewModel.bedStatusResponse.observe(this,  { response ->
                if(response.isSuccessful){
                    binding.textViewResponse.text = response.body()?.toString()
                }else
                    binding.textViewResponse.text = response.errorBody().toString()

            })
        }

        binding.buttonOverviewActivity.setOnClickListener {
            val intent = Intent(this, SystemOverview::class.java)
            startActivity(intent)
        }
    }

}