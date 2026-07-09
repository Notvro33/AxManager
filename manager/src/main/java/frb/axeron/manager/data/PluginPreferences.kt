package frb.axeron.manager.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import frb.axeron.manager.AxeronApplication

object PluginPreferences {
    private const val PREFS_NAME = "plugin_settings"
    private const val KEY_ENABLED_PLUGINS = "enabled_plugins"
    private const val KEY_PERSISTENT_MODE = "persistent_mode"
    private const val KEY_NATIVE_BOOST = "native_boost_enabled"
    private const val KEY_OFFLINE_ADB = "offline_adb_enabled"
    private const val KEY_LOCAL_CONFIG_DIR = "local_config_dir"
    private const val KEY_PLUGIN_LIST = "plugin_list"

    private val prefs: SharedPreferences by lazy {
        AxeronApplication.axeronApp.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    fun savePluginSettings(settings: PluginSettings) {
        prefs.edit().apply {
            putStringSet(KEY_ENABLED_PLUGINS, settings.enabledPlugins.toSet())
            putBoolean(KEY_PERSISTENT_MODE, settings.persistentMode)
            putBoolean(KEY_NATIVE_BOOST, settings.nativeBoostEnabled)
            putBoolean(KEY_OFFLINE_ADB, settings.offlineAdbEnabled)
            putString(KEY_LOCAL_CONFIG_DIR, settings.localConfigDir)
            apply()
        }
    }

    fun getPluginSettings(): PluginSettings {
        return PluginSettings(
            enabledPlugins = prefs.getStringSet(KEY_ENABLED_PLUGINS, emptySet())?.toList() ?: emptyList(),
            persistentMode = prefs.getBoolean(KEY_PERSISTENT_MODE, false),
            nativeBoostEnabled = prefs.getBoolean(KEY_NATIVE_BOOST, false),
            offlineAdbEnabled = prefs.getBoolean(KEY_OFFLINE_ADB, false),
            localConfigDir = prefs.getString(KEY_LOCAL_CONFIG_DIR, "") ?: ""
        )
    }

    fun savePluginList(plugins: List<PluginConfig>) {
        val json = gson.toJson(plugins)
        prefs.edit().putString(KEY_PLUGIN_LIST, json).apply()
    }

    fun getPluginList(): List<PluginConfig> {
        val json = prefs.getString(KEY_PLUGIN_LIST, "[]") ?: "[]"
        val type = object : com.google.gson.reflect.TypeToken<List<PluginConfig>>() {}.type
        return gson.fromJson(json, type)
    }

    fun updatePluginEnabled(pluginId: String, enabled: Boolean) {
        val settings = getPluginSettings()
        val updatedPlugins = if (enabled) {
            (settings.enabledPlugins + pluginId).distinct()
        } else {
            settings.enabledPlugins.filter { it != pluginId }
        }
        savePluginSettings(settings.copy(enabledPlugins = updatedPlugins))
    }

    fun setPersistentMode(enabled: Boolean) {
        val settings = getPluginSettings()
        savePluginSettings(settings.copy(persistentMode = enabled))
    }

    fun setNativeBoost(enabled: Boolean) {
        val settings = getPluginSettings()
        savePluginSettings(settings.copy(nativeBoostEnabled = enabled))
    }

    fun setOfflineAdb(enabled: Boolean) {
        val settings = getPluginSettings()
        savePluginSettings(settings.copy(offlineAdbEnabled = enabled))
    }

    fun setLocalConfigDir(dir: String) {
        val settings = getPluginSettings()
        savePluginSettings(settings.copy(localConfigDir = dir))
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
