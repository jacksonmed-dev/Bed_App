package com.jacksonmed.bed.activities.overview.patient

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
    ): View {
        _binding = PatientFragmentBinding.inflate(inflater, container, false)

        val repository = RepositoryPatient()
        val viewModelFactory = PatientViewModelFactory(repository)
        patientViewModel = ViewModelProvider(this, viewModelFactory).get(PatientViewModel::class.java)

        // Get the patient info
        patientViewModel.getPatientInfo()
        patientViewModel.patientInfo.observe(viewLifecycleOwner, { response ->
            patient = response.body()
            setPatientInfo()
        })

        // Get the current patient pressure model
        patientViewModel.getMaxPressure()
        patientViewModel.maxPressure.observe(viewLifecycleOwner, { response ->
            patientPressure = response.body()
            setPatientPressure()
            patientPressureMapping(patientPressure!!)               //Fix this!! Bad design
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setImageResource(R.drawable.body_3d)
        binding.imageView.tag = R.drawable.body_3d


        binding.buttonGetPressure.setOnClickListener {
            patientViewModel.getMaxPressure()
            patientViewModel.maxPressure.observe(viewLifecycleOwner, { response ->
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

    }

    private fun patientPressureMapping(patientPressure: PatientPressure){
        val head: Int = ((patientPressure.head/MAX_PRESSURE) * 360).toInt()
        changeImgHue(head, 0, 15)

        val shoulders: Int = ((patientPressure.shoulders/MAX_PRESSURE) * 360).toInt()
        changeImgHue(shoulders, 15, 23)

        val back: Int = ((patientPressure.back/MAX_PRESSURE) * 360).toInt()
        changeImgHue(back, 23, 40)

        val butt: Int = ((patientPressure.butt/MAX_PRESSURE) * 360).toInt()
        changeImgHue(butt, 40, 53)

        val calves: Int = ((patientPressure.calves/MAX_PRESSURE) * 360).toInt()
        changeImgHue(calves, 53, 95)

        val feet: Int = ((patientPressure.feet/MAX_PRESSURE) * 360).toInt()
        changeImgHue(feet, 95, 100)
    }

    private fun changeImgHue(hue: Int, heightStart: Int, heightEnd: Int){
        val imgView: ImageView = binding.imageView
        val drawable: BitmapDrawable = imgView.drawable as BitmapDrawable
        val bm: Bitmap = Hue.changeHue(drawable.bitmap, hue, drawable.bitmap.width, heightStart, heightEnd)

        imgView.setImageBitmap(bm)
    }

    private fun setPatientPressure(){
        if(patientPressure == null) return
        binding.textViewHeadPressure.text = getString(R.string.head_pressure, patientPressure!!.head)
        binding.textViewShoulderPressure.text = getString(R.string.shoulder_pressure, patientPressure!!.shoulders)
        binding.textViewBackPressure.text = getString(R.string.back_pressure, patientPressure!!.back)
        binding.textViewButtPressure.text = getString(R.string.butt_pressure, patientPressure!!.butt)
        binding.textViewCalvesPressure.text = getString(R.string.calve_pressure, patientPressure!!.calves)
        binding.textViewFeetPressure.text = getString(R.string.foot_pressure, patientPressure!!.feet)
    }

    private fun setPatientInfo(){
        if(patient == null) return
        binding.textViewPatientName.text = getString(R.string.patient_name, patient!!.firstName, patient!!.lastName)
        binding.textViewPatientNameCard.text = getString(R.string.patient_name, patient!!.firstName, patient!!.lastName)
        binding.textViewPatientAge.text = getString(R.string.patient_age, patient!!.age)
        binding.textViewPatientHeight.text = getString(R.string.patient_height, patient!!.height)
        binding.textViewPatientWeight.text = getString(R.string.patient_weight, patient!!.weight)
    }

    companion object {
        fun newInstance() = PatientFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}