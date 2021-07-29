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
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.repository.RepositoryPatient
import com.jacksonmed.bed.utils.Constants.Companion.MAX_PRESSURE
import com.jacksonmed.bed.utils.Hue

class PatientFragment : Fragment() {
    private lateinit var patientViewModel: PatientViewModel

    private var _binding: PatientFragmentBinding? = null
    private val binding get() = _binding!!

    private var imgBool: Boolean = true
    private var colorBool: Boolean = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PatientFragmentBinding.inflate(inflater, container, false)

        val repository = RepositoryPatient()
        val viewModelFactory = PatientViewModelFactory(repository)
        patientViewModel = ViewModelProvider(this, viewModelFactory).get(PatientViewModel::class.java)

        return binding.root
    }
    // Change this to onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientViewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        binding.imageView.setImageResource(R.drawable.body_3d)
        binding.imageView.tag = R.drawable.body_3d

        binding.textViewSeekBar.text = binding.seekBar.progress.toString()

        binding.buttonGetPressure.setOnClickListener {
            patientViewModel.getMaxPressure()
            patientViewModel.maxPressure.observe(viewLifecycleOwner, Observer { response ->
                val patientPressure: PatientPressure? = response.body()
                if(patientPressure != null)
                    patientPressureMapping(patientPressure = patientPressure)

            })
        }

        binding.buttonImgChanger.setOnClickListener {
            val imgView: ImageView = binding.imageView
            if(imgView.tag == R.drawable.body_back_3d) {
                imgView.setImageResource(R.drawable.body_3d)
                imgView.tag = R.drawable.body_3d
            } else if (imgView.tag == R.drawable.body_3d) {
                imgView.setImageResource(R.drawable.body_back_3d)
                imgView.tag = R.drawable.body_back_3d
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                changeImgHue(seekBar!!.progress, 0, binding.seekBarColorPercentage!!.progress)
                binding.textViewSeekBar.text = binding.seekBar.progress.toString()
            }
        })

        binding.seekBarColorPercentage.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                changeImgHue(binding.seekBar!!.progress, 0, seekBar!!.progress)
                binding.textViewSeekBar.text = binding.seekBar.progress.toString()
            }

        })
    }

    fun patientPressureMapping(patientPressure: PatientPressure){
        val head: Int = ((patientPressure.head/MAX_PRESSURE) * 360).toInt()
        changeImgHue(head, 0, 15)

        val shoulders: Int = ((patientPressure.shoulders/MAX_PRESSURE) * 360).toInt()
        changeImgHue(shoulders, 15, 20)

        val back: Int = ((patientPressure.back/MAX_PRESSURE) * 360).toInt()
        changeImgHue(150, 20, 45)

        val butt: Int = ((patientPressure.butt/MAX_PRESSURE) * 360).toInt()
        changeImgHue(butt, 45, 55)

        val calves: Int = ((patientPressure.calves/MAX_PRESSURE) * 360).toInt()
        changeImgHue(calves, 55, 80)

        val feet: Int = ((patientPressure.feet/MAX_PRESSURE) * 360).toInt()
        changeImgHue(feet, 80, 100)
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

    companion object {
        fun newInstance() = PatientFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}