package com.jacksonmed.bed.activities.overview.patient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.R
import com.jacksonmed.bed.databinding.PatientFragmentBinding
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.repository.RepositoryPatient
import com.jacksonmed.bed.utils.Constants.Companion.MAX_PRESSURE
import com.jacksonmed.bed.utils.Hue

class PatientFragment : Fragment() {
    private lateinit var patientViewModel: PatientViewModel

    private var _binding: PatientFragmentBinding? = null
    private val binding get() = _binding!!

    private var patient: Patient? = null
    private var patientPressure: PatientPressure? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PatientFragmentBinding.inflate(inflater, container, false)

        val repository = RepositoryPatient()
        val viewModelFactory = PatientViewModelFactory(repository)
        patientViewModel = ViewModelProvider(this, viewModelFactory).get(PatientViewModel::class.java)

        // Get the patient info
        patientViewModel.getPatientInfo()
        patientViewModel.patientInfo.observe(viewLifecycleOwner, Observer { response ->
            patient = response.body()
            setPatientInfo()
        })

        // Get the current patient pressure model
        patientViewModel.getMaxPressure()
        patientViewModel.maxPressure.observe(viewLifecycleOwner, Observer { response ->
            patientPressure = response.body()
            setPatientPressure()
            patientPressureMapping(patientPressure!!)               //Fix this!! Bad design
        })

        return binding.root
    }
    // Change this to onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        patientViewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        binding.imageView.setImageResource(R.drawable.body_3d)
        binding.imageView.tag = R.drawable.body_3d


        binding.buttonGetPressure.setOnClickListener {
            patientViewModel.getMaxPressure()
            patientViewModel.maxPressure.observe(viewLifecycleOwner, Observer { response ->
                patientPressure = response.body()
                if(patientPressure != null)
                    patientPressureMapping(patientPressure = patientPressure!!)

            })
        }

        binding.checkBox.setOnClickListener {
            val imgView: ImageView = binding.imageView
            if(imgView.tag == R.drawable.body_back_3d) {
                imgView.setImageResource(R.drawable.body_3d)
                imgView.tag = R.drawable.body_3d
                patientPressureMapping(patientPressure!!)
            } else if (imgView.tag == R.drawable.body_3d) {
                imgView.setImageResource(R.drawable.body_back_3d)
                imgView.tag = R.drawable.body_back_3d
                patientPressureMapping(patientPressure!!)
            }
        }

//        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                changeImgHue(seekBar!!.progress, 0, binding.seekBarColorPercentage!!.progress)
//                binding.textViewSeekBar.text = binding.seekBar.progress.toString()
//            }
//        })

//        binding.seekBarColorPercentage.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                changeImgHue(binding.seekBar!!.progress, 0, seekBar!!.progress)
//                binding.textViewSeekBar.text = binding.seekBar.progress.toString()
//            }
//
//        })
    }

    fun patientPressureMapping(patientPressure: PatientPressure){
        val head: Int = ((patientPressure.head/MAX_PRESSURE) * 360).toInt()
        changeImgHue(head, 0, 15)

        val shoulders: Int = ((patientPressure.shoulders/MAX_PRESSURE) * 360).toInt()
        changeImgHue(shoulders, 15, 23)

        val back: Int = ((patientPressure.back/MAX_PRESSURE) * 360).toInt()
        changeImgHue(150, 23, 40)

        val butt: Int = ((patientPressure.butt/MAX_PRESSURE) * 360).toInt()
        changeImgHue(butt, 40, 53)

        val calves: Int = ((patientPressure.calves/MAX_PRESSURE) * 360).toInt()
        changeImgHue(calves, 53, 95)

        val feet: Int = ((patientPressure.feet/MAX_PRESSURE) * 360).toInt()
        changeImgHue(feet, 95, 100)
    }

    fun changeImgHue(hue: Int, heightStart: Int, heightEnd: Int){
        val imgView: ImageView = binding.imageView
        val drawable: BitmapDrawable = imgView.drawable as BitmapDrawable
        var bm: Bitmap = drawable.bitmap
//        var bm: Bitmap = BitmapFactory.decodeResource(resources, imgView.tag as Int)

        var bmCopy: Bitmap = Hue.changeHue(bm, hue, bm.width, heightStart, heightEnd)
        imgView.setImageBitmap(bmCopy)

        imgView.setImageBitmap(bmCopy)
    }

    fun setPatientPressure(){
        if(patientPressure == null) return
        binding.textViewHeadPressure.text = "Head:\t" + patientPressure!!.head.toString()
        binding.textViewShoulderPressure.text = "Shoulders:\t" + patientPressure!!.shoulders.toString()
        binding.textViewBackPressure.text = "Back:\t" + patientPressure!!.back.toString()
        binding.textViewButtPressure.text = "Butt:\t" + patientPressure!!.butt.toString()
        binding.textViewCalvesPressure.text = "Calves:\t" + patientPressure!!.calves.toString()
        binding.textViewFeetPressure.text = "Feet:\t" + patientPressure!!.feet.toString()
    }

    fun setPatientInfo(){
        if(patient == null) return
        binding.textViewPatientName.text = "Name:\t" +  patient!!.firstName + " " + patient!!.lastName
        binding.textViewPatientNameCard.text = "Name:\t" +  patient!!.firstName + " " + patient!!.lastName
        binding.textViewPatientAge.text = "Age:\t" + patient!!.age
        binding.textViewPatientHeight.text = "Height:\t" + patient!!.height
        binding.textViewPatientWeight.text = "Weight:\t" + patient!!.weight
    }

    companion object {
        fun newInstance() = PatientFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}