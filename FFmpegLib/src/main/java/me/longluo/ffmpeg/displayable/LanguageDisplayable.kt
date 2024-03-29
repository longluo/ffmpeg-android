package me.longluo.ffmpeg.displayable

import android.os.Build
import me.longluo.ffmpeg.BasicStreamInfo
import java.util.Locale

val BasicStreamInfo.displayableLanguage: String?
    get() = language?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val capitalizedLanguage = (Locale.forLanguageTag(language).getDisplayLanguage(Locale.US)
                ?: language).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

            if (capitalizedLanguage.isNotEmpty()) {
                return capitalizedLanguage
            }
        }
        return null
    }
