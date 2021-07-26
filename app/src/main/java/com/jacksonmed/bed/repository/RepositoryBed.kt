package com.jacksonmed.bed.repository

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.jacksonmed.bed.R
import com.jacksonmed.bed.activities.overview.bed.Drawable.DrawableFragment
import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.StatusResponse
import retrofit2.Response

class RepositoryBed {
    suspend fun startMassage(): Response<StatusResponse> {
        return RetrofitInstance.api.startMassage()
    }

    suspend fun stopMassage(): Response<StatusResponse> {
        return RetrofitInstance.api.stopMassage()
    }

    suspend fun getBedStatus(): Response<Bed> {
        return RetrofitInstance.api.getBedStatus()
    }
// This function will have poor performance!!!! Re write to update all values at once vs one at a time
    // Another potential Bug: GPIO data object has pin: Int, value: Int. The list index might not match
    // with pin value
    fun setBedDrawable(response: Response<Bed>, drawableFragment: DrawableFragment, context: Context){
        var colorOn: Int = ContextCompat.getColor(context, R.color.jacksonmed_blue)
        var colorOff: Int = ContextCompat.getColor(context, R.color.jacksonmed_gray)

        val gpio = response.body()?.gpioPins
        if (gpio != null) {
            gpio.forEachIndexed{index, element ->
                if(element.state == 0) {
                    drawableFragment.setColor(index, colorOff)
                }else if(element.state == 1)
                    drawableFragment.setColor(index, colorOn)
            }
        }
    }
}