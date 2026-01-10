package hoods.com.jetshopping

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

