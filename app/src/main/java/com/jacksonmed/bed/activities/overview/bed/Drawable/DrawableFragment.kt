package com.jacksonmed.bed.activities.overview.bed.Drawable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jacksonmed.bed.R

import com.jacksonmed.bed.activities.overview.bed.BedViewModel



class DrawableFragment: Fragment() {
    private lateinit var bedDrawableView: BedDrawableView
    private val viewModel: BedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bedDrawableView = BedDrawableView(requireContext())

        return bedDrawableView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bedStatusResponse.observe(viewLifecycleOwner, Observer { response ->
            var colorOn: Int = ContextCompat.getColor(requireContext(), R.color.jacksonmed_blue)
            var colorOff: Int = ContextCompat.getColor(requireContext(), R.color.jacksonmed_gray)

            val gpio = response.body()?.gpioPins
            if (gpio != null) {
                gpio.forEachIndexed{index, element ->
                    if(element.state == 0) {
                        setColor(index, colorOff)
                    }else if(element.state == 1)
                        setColor(index, colorOn)
                }
            }
        })
    }

    fun setColor(index: Int, color: Int){
        bedDrawableView.changeRectColor(index, color)
    }

    companion object {
        fun newInstance() = DrawableFragment()
    }
}

