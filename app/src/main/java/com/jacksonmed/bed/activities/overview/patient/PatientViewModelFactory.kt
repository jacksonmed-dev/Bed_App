package com.jacksonmed.bed.activities.overview.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksonmed.bed.repository.RepositoryPatient

class PatientViewModelFactory(private val repository: RepositoryPatient): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientViewModel(repository) as T
    }

}