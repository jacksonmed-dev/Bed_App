package com.jacksonmed.bed

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Use for dependency injection
@HiltAndroidApp
class BaseApplication: Application() {
}