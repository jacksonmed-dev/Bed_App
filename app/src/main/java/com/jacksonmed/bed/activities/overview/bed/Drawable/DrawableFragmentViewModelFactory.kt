package com.jacksonmed.bed.activities.overview.bed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.repository.RepositoryBed

class DrawableFragmentViewModelFactory(private val repository: RepositoryBed): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrawableFragmentViewModel(repository) as T
    }
}