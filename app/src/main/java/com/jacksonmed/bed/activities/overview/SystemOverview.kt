package com.jacksonmed.bed.activities.overview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jacksonmed.bed.R
import com.jacksonmed.bed.activities.overview.bed.BedViewModel
import com.jacksonmed.bed.activities.overview.bed.BedViewModelFactory
import com.jacksonmed.bed.databinding.ActivitySystemOverviewBinding
import com.jacksonmed.bed.repository.RepositoryBed

class SystemOverview : AppCompatActivity() {
    //test commit message

    private lateinit var binding: ActivitySystemOverviewBinding
    private lateinit var viewModel: BedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySystemOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.bottomNavigation.setBackgroundColor(ContextCompat.getColor(applicationContext, android.R.color.transparent))

        val repository = RepositoryBed()
        val viewModelFactory = BedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BedViewModel::class.java)


        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
}