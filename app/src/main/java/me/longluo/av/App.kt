package me.longluo.av

import android.app.Application
import me.longluo.av.presentation.settings.ThemeManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeManager.initNightModePreference(this)
    }

}
