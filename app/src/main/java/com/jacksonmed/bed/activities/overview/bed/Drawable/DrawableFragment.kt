package com.jacksonmed.bed.activities.overview.bed.Drawable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DrawableFragment: Fragment() {
    private lateinit var bedDrawableView: BedDrawableView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bedDrawableView = BedDrawableView(requireContext())
        return bedDrawableView
    }

    fun clickEvent(value: Int){
        bedDrawableView.changeRectColor(value)
    }

    companion object {
        fun newInstance() = DrawableFragment()
    }
}