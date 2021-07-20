package com.jacksonmed.bed.activities.overview

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.jacksonmed.bed.activities.overview.tabview.SectionsPagerAdapter
import com.jacksonmed.bed.databinding.ActivitySystemOverviewBinding

class SystemOverview : AppCompatActivity() {

    private lateinit var binding: ActivitySystemOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySystemOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}