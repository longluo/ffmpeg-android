package me.longluo.ffmpeg

/**
 * Represents metadata of a subtitle stream in a media file.
 */
class SubtitleStream internal constructor(
    override val basicInfo: BasicStreamInfo
) : MediaStream
