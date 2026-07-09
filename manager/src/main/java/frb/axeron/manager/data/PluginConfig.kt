package frb.axeron.manager.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PluginConfig(
    val id: String,
    val name: String,
    val version: String,
    val isEnabled: Boolean = true,
    val isPersistent: Boolean = false,
    val isNative: Boolean = false,
    val configPath: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class PluginSettings(
    val enabledPlugins: List<String> = emptyList(),
    val persistentMode: Boolean = false,
    val nativeBoostEnabled: Boolean = false,
    val offlineAdbEnabled: Boolean = false,
    val localConfigDir: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
