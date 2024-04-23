package me.longluo.av.presentation.video

import android.app.Activity
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.longluo.av.R
import me.longluo.av.presentation.compose.common.GridLayout
import me.longluo.av.presentation.root.viewmodel.model.BasicVideoInfo
import me.longluo.av.presentation.settings.PreferencesKeys
import me.longluo.av.presentation.stream.StreamCard
import me.longluo.av.presentation.stream.StreamFeature
import me.longluo.av.presentation.stream.StreamFeatureItem
import me.longluo.av.presentation.stream.StreamFeaturesGrid
import me.longluo.av.presentation.stream.getFilteredStreamFeatures
import me.longluo.av.presentation.stream.makeCardTitle
import me.longluo.ffmpeg.VideoStream
import me.longluo.ffmpeg.displayable.displayableLanguage
import me.longluo.ffmpeg.displayable.getDisplayableDisposition
import me.longluo.ffmpeg.displayable.toDisplayable


@Composable
fun VideoPage(
    videoInfo: BasicVideoInfo,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            FramesHeader(videoInfo.preview, getPreviewViewWidth(LocalContext.current as Activity))
        }
        val commonModifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        item {
            Container(
                basicVideoInfo = videoInfo,
                modifier = commonModifier
                    .padding(top = 16.dp)
            )
        }
        item {
            VideoStream(
                videoStream = videoInfo.videoStream,
                modifier = commonModifier
            )
        }
    }
}

@Composable
private fun Container(
    basicVideoInfo: BasicVideoInfo,
    modifier: Modifier = Modifier
) {
    StreamCard(
        title = stringResource(id = R.string.info_container),
        modifier = modifier
    ) {
        GridLayout(modifier = it, columns = 2) {
            StreamFeatureItem(
                title = stringResource(id = R.string.info_file_format).toUpperCase(),
                value = basicVideoInfo.fileFormat
            )
            StreamFeatureItem(
                title = stringResource(id = R.string.info_protocol_title).toUpperCase(),
                value = stringResource(
                    id = if (basicVideoInfo.fullFeatured) {
                        R.string.info_protocol_file
                    } else {
                        R.string.info_protocol_pipe
                    }
                )
            )
        }
    }
}

@Composable
private fun VideoStream(
    videoStream: VideoStream,
    modifier: Modifier = Modifier
) {
    StreamCard(
        title = makeCardTitle(videoStream.basicInfo),
        modifier
    ) {
        val streamFeatures = getFilteredStreamFeatures(
            defaultValueResId = R.array.settings_content_video_entryValues,
            preferenceKey = PreferencesKeys.VIDEO,
            allValues = VideoFeature.values().toList()
        )

        StreamFeaturesGrid(
            stream = videoStream,
            features = streamFeatures,
            modifier = it
        )
    }
}

private enum class VideoFeature(
    @StringRes override val key: Int,
    @StringRes override val title: Int
) :
    StreamFeature<VideoStream> {

    CODEC(
        key = R.string.settings_content_codec,
        title = R.string.page_video_codec_name
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.basicInfo.codecName
    },
    BITRATE(
        key = R.string.settings_content_bitrate,
        title = R.string.page_video_bit_rate
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.bitRate.toDisplayable(resources)
    },
    FRAME_RATE(
        key = R.string.settings_content_video_frame_rate,
        title = R.string.page_video_frame_rate
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.frameRate.toDisplayable(resources)
    },
    FRAME_WIDTH(
        key = R.string.settings_content_video_width,
        title = R.string.page_video_frame_width
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.frameWidth.toString()
    },
    FRAME_HEIGHT(
        key = R.string.settings_content_video_height,
        title = R.string.page_video_frame_height
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.frameHeight.toString()
    },
    LANGUAGE(
        key = R.string.settings_content_language,
        title = R.string.page_stream_language
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.basicInfo.displayableLanguage
    },
    DISPOSITION(
        key = R.string.settings_content_disposition,
        title = R.string.page_stream_disposition
    ) {
        override fun getValue(stream: VideoStream, resources: Resources) =
            stream.basicInfo.getDisplayableDisposition(resources)
    };
}
