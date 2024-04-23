package me.longluo.av.presentation.subtitle

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.longluo.av.R
import me.longluo.av.presentation.settings.PreferencesKeys
import me.longluo.av.presentation.stream.SimplePage
import me.longluo.av.presentation.stream.StreamFeature
import me.longluo.av.presentation.stream.StreamFeaturesGrid
import me.longluo.av.presentation.stream.getFilteredStreamFeatures
import me.longluo.ffmpeg.SubtitleStream
import me.longluo.ffmpeg.displayable.displayableLanguage
import me.longluo.ffmpeg.displayable.getDisplayableDisposition

@Composable
fun SubtitlePage(
    streams: List<SubtitleStream>,
    modifier: Modifier = Modifier
) {
    SimplePage(streams, modifier) { item, itemModifier ->
        SubtitleCardContent(item, itemModifier)
    }
}

@Composable
private fun SubtitleCardContent(
    stream: SubtitleStream,
    modifier: Modifier = Modifier
) {
    val streamFeatures = getFilteredStreamFeatures(
        defaultValueResId = R.array.settings_content_subtitles_entryValues,
        preferenceKey = PreferencesKeys.SUBTITLES,
        allValues = SubtitleFeature.values().toList()
    )

    StreamFeaturesGrid(
        stream,
        streamFeatures,
        modifier
    )
}

private enum class SubtitleFeature(
    @StringRes override val key: Int,
    @StringRes override val title: Int
) :
    StreamFeature<SubtitleStream> {

    CODEC(
        key = R.string.settings_content_codec,
        title = R.string.page_subtitle_codec_name
    ) {
        override fun getValue(stream: SubtitleStream, resources: Resources) =
            stream.basicInfo.codecName
    },
    LANGUAGE(
        key = R.string.settings_content_language,
        title = R.string.page_stream_language
    ) {
        override fun getValue(stream: SubtitleStream, resources: Resources) =
            stream.basicInfo.displayableLanguage
    },
    DISPOSITION(
        key = R.string.settings_content_disposition,
        title = R.string.page_stream_disposition
    ) {
        override fun getValue(stream: SubtitleStream, resources: Resources) =
            stream.basicInfo.getDisplayableDisposition(resources)
    };
}
