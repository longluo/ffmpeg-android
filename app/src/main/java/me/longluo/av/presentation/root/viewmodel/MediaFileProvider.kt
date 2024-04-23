package me.longluo.av.presentation.root.viewmodel

import android.content.Context
import me.longluo.ffmpeg.MediaFile
import me.longluo.ffmpeg.creator.MediaFileCreator


class MediaFileProvider(context: Context) {
    private val mediaFileCreator = MediaFileCreator(context)

    fun obtainMediaFile(argument: MediaFileArgument): MediaFile? {
        return mediaFileCreator.createMediaFile(argument.uri, argument.type)
    }
}
