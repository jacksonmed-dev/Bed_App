package com.jacksonmed.bed.activities.overview.bed.inflatable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import com.jacksonmed.bed.activities.overview.bed.BedViewModel
import com.jacksonmed.bed.activities.overview.bed.DrawableFragmentViewModel
import com.jacksonmed.bed.activities.overview.bed.DrawableFragmentViewModelFactory
import com.jacksonmed.bed.repository.RepositoryBed


class DrawableFragment: Fragment() {

    private val bedViewModel: BedViewModel by activityViewModels()
    private lateinit var drawableFragmentViewModel: DrawableFragmentViewModel

    private lateinit var bedDrawableView: BedDrawableView
//    private var inflatableRegions: Int? = null
    private var inflatableRegions: Int = 20


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val repository = RepositoryBed()
        val viewModelFactory = DrawableFragmentViewModelFactory(repository)
        drawableFragmentViewModel = ViewModelProvider(this, viewModelFactory).get(DrawableFragmentViewModel::class.java)

        bedDrawableView = BedDrawableView(requireContext())

        return bedDrawableView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bedDrawableView.createRectangles(inflatableRegions)
//        bedViewModel.bluetoothResponse.observe(viewLifecycleOwner, Observer { response ->
//            var colorOn: Int = ContextCompat.getColor(requireContext(), R.color.primary)
//            var colorOff: Int = ContextCompat.getColor(requireContext(), R.color.gray)
////            val jsonData: Bed = Json.decodeFromString(Bed.serializer(), response)
////            jsonData.gpioPins.forEach { pin -> bedDrawableView.changeRectColor(pin.state, colorOn) }
//        })

//        drawableFragmentViewModel.getBedStatus().observe(viewLifecycleOwner, Observer { response ->
//            val gpio = response.response?.body()?.gpioPins?.size
//            inflatableRegions = gpio!!
//            if(inflatableRegions != null) {
//                bedDrawableView.createRectangles(inflatableRegions!!)
//            }
//
//        })
//
//
//        bedViewModel.getBedStatus().observe(viewLifecycleOwner, Observer { response ->
//            var colorOn: Int = ContextCompat.getColor(requireContext(), R.color.primary)
//            var colorOff: Int = ContextCompat.getColor(requireContext(), R.color.gray)
//
//            val gpio = response.response?.body()?.gpioPins
//            if (gpio != null) {
//                gpio.forEachIndexed{index, element ->
//                    if(element.state == 0) {
//                        setColor(index, colorOff)
//                    }else if(element.state == 1)
//                        setColor(index, colorOn)
//                }
//            }
//        })
    }

    fun setColor(index: Int, color: Int){
        bedDrawableView.changeRectColor(index, color)
    }

    companion object {
        fun newInstance() = DrawableFragment()
    }
    // Potential Memory Leak because the bedDrawableView is not set to null in onDestroyView()
}

