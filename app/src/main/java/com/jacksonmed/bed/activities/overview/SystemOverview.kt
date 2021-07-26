package com.jacksonmed.bed.activities.overview

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.activities.overview.bed.BedViewModel
import com.jacksonmed.bed.activities.overview.bed.BedViewModelFactory
import com.jacksonmed.bed.activities.overview.tabview.SectionsPagerAdapter
import com.jacksonmed.bed.databinding.ActivitySystemOverviewBinding
import com.jacksonmed.bed.repository.RepositoryBed

class SystemOverview : AppCompatActivity() {

    private lateinit var binding: ActivitySystemOverviewBinding
//    private val viewModel: BedViewModel by viewModels<BedViewModel>(BedViewModelFactory(RepositoryBed()))
    private lateinit var viewModel: BedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySystemOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repository = RepositoryBed()
        val viewModelFactory = BedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BedViewModel::class.java)



        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}