package me.longluo.av.presentation.root.viewmodel.model

import me.longluo.ffmpeg.VideoStream

data class BasicVideoInfo(
    val preview: Preview,
    val fileFormat: String,
    val fullFeatured: Boolean,
    val videoStream: VideoStream
)
