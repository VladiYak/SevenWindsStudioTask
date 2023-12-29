package com.vladiyak.sevenwindsstudiotask

import android.app.Application
import com.vladiyak.sevenwindsstudiotask.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.API_KEY)

    }
}