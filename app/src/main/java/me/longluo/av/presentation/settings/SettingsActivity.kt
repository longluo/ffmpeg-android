package me.longluo.av.presentation.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import me.longluo.av.R
import me.longluo.av.presentation.compose.common.WtcTopAppBar
import me.longluo.av.presentation.compose.preference.ListPreference
import me.longluo.av.presentation.compose.preference.MultiSelectListPreference
import me.longluo.av.presentation.compose.preference.Preference
import me.longluo.av.presentation.compose.preference.PreferenceDivider
import me.longluo.av.presentation.compose.preference.PreferenceTitle
import me.longluo.av.presentation.compose.theme.WhatTheCodecTheme

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContent {
            WhatTheCodecTheme {
                SettingsScreen()
            }
        }
    }

    private fun openUrl(url: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(this, Uri.parse(url))
    }

    @Composable
    fun SettingsScreen() {
        Scaffold(
            topBar = {
                SettingsTopAppBar()
            }
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            ) {
                SettingsContent()
            }
        }
    }

    @Composable
    private fun SettingsContent() {
        PreferenceTitle(title = R.string.settings_category_general)
        ThemeSelectionPreference()

        PreferenceDivider()

        PreferenceTitle(title = R.string.settings_category_content)
        PreferredVideoContentPreference()
        PreferredAudioContentPreference()
        PreferredSubtitlesContentPreference()

        PreferenceDivider()

        PreferenceTitle(title = R.string.settings_category_about)
        OpenUrlPreference(
            title = R.string.settings_source_code_title,
            summary = R.string.settings_source_code_summary
        )
        OpenUrlPreference(
            title = R.string.settings_privacy_policy_title,
            summary = R.string.settings_privacy_policy_summary
        )
    }

    @Composable
    private fun SettingsTopAppBar() {
        WtcTopAppBar(
            title = { Text(text = stringResource(id = R.string.settings_title)) },
            navigationIcon = {
                IconButton(onClick = { onSupportNavigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_back)
                    )
                }
            })
    }

    @Composable
    private fun ThemeSelectionPreference() {
        ListPreference(
            "theme",
            defaultValue = stringResource(id = R.string.settings_theme_default_pref_value),
            title = stringResource(id = R.string.settings_theme_title),
            displayableEntries = stringArrayResource(id = R.array.settings_theme_entries).toList(),
            entriesCodes = stringArrayResource(id = R.array.settings_theme_entryValues).toList()
        ) {
            ThemeManager.setNightModePreference(it)
        }
    }

    @Composable
    private fun PreferredVideoContentPreference() {
        val entriesCodes =
            stringArrayResource(id = R.array.settings_content_video_entryValues).toList()
        MultiSelectListPreference(
            PreferencesKeys.VIDEO,
            defaultValue = entriesCodes.toSet(),
            title = stringResource(id = R.string.settings_content_video_title),
            displayableEntries = stringArrayResource(id = R.array.settings_content_video_entries).toList(),
            entriesCodes = entriesCodes
        )
    }

    @Composable
    private fun PreferredAudioContentPreference() {
        val entriesCodes =
            stringArrayResource(id = R.array.settings_content_audio_entryValues).toList()
        MultiSelectListPreference(
            PreferencesKeys.AUDIO,
            defaultValue = entriesCodes.toSet(),
            title = stringResource(id = R.string.settings_content_audio_title),
            displayableEntries = stringArrayResource(id = R.array.settings_content_audio_entries).toList(),
            entriesCodes = entriesCodes
        )
    }

    @Composable
    private fun PreferredSubtitlesContentPreference() {
        val entriesCodes =
            stringArrayResource(id = R.array.settings_content_subtitles_entryValues).toList()
        MultiSelectListPreference(
            PreferencesKeys.SUBTITLES,
            defaultValue = entriesCodes.toSet(),
            title = stringResource(id = R.string.settings_content_subtitles_title),
            displayableEntries = stringArrayResource(id = R.array.settings_content_subtitles_entries).toList(),
            entriesCodes = entriesCodes
        )
    }

    @Composable
    private fun OpenUrlPreference(@StringRes title: Int, @StringRes summary: Int) {
        val url = stringResource(id = summary)
        Preference(title = title, summary = summary) {
            openUrl(url)
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}