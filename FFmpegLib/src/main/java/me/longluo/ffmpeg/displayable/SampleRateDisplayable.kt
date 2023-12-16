package me.longluo.ffmpeg.displayable

import android.content.res.Resources
import me.longluo.ffmpeg.R
import me.longluo.ffmpeg.SampleRate
import java.text.DecimalFormat

fun SampleRate.toDisplayable(resources: Resources): String? {
    return if (this > 0) {
        val formattedSampleRate = DecimalFormat("0.#").format(this / 1000f)
        formattedSampleRate + " " + resources.getString(R.string.media_file_audio_sample_rate_postfix)
    } else {
        null
    }
}