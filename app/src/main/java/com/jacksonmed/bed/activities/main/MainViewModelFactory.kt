package com.jacksonmed.bed.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.repository.Repository

class MainViewModelFactory(private val repository:Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}