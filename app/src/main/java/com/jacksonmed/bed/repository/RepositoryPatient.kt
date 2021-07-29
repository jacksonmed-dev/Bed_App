package com.jacksonmed.bed.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.utils.Hue
import retrofit2.Response

class RepositoryPatient {
    suspend fun getPatientPressure(): Response<PatientPressure> {
        return RetrofitInstance.api.getMaxPressure()
    }

    suspend fun getPatientInfo(): Response<Patient> {
        return RetrofitInstance.api.getPatientInfo()
    }

}