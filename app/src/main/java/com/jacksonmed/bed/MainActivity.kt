package com.jacksonmed.bed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jacksonmed.bed.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*


const val PI_URL = "http://192.168.86.122:5000/"
var bedState: Boolean = false

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()?.userId.toString())
                Log.d("Response", response.body()?.id.toString())
                Log.d("Response", response.body()?.title!!)
                Log.d("Response", response.body()?.body!!)
            }else {
                Log.d("Response", response.errorBody().toString())
            }

        })




        textView.text = "Hello World"

        button.setOnClickListener { v ->
//            bedState = !bedState
//            toggleBedSwitch(1, if(bedState) 1 else 0)
            relayTest()
        }
    }

    private fun toggleBedSwitch(did: Int, state: Int){
        val url = "https://api.coolebed.com/bed-api/device/fanPower?did=$did&state=$state"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                textView.text = "Bed State $state"
            },
            Response.ErrorListener { textView.text = "Error switching bed state" })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

        queue.add(stringRequest)
    }

    private fun relayTest(){
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Request.Method.GET, PI_URL,
            Response.Listener<String> { response ->
                textView.text = "Its working"
            },
            Response.ErrorListener { textView.text = "Error toggling relays" })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue.add(stringRequest)
    }

}