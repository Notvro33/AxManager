package frb.axeron.manager.features

import android.content.Context
import frb.axeron.manager.data.PluginConfig
import frb.axeron.manager.data.PluginPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PluginManager(private val context: Context) {

    private val _plugins = MutableStateFlow<List<PluginConfig>>(emptyList())
    val plugins: StateFlow<List<PluginConfig>> = _plugins

    private val _persistentModeEnabled = MutableStateFlow(false)
    val persistentModeEnabled: StateFlow<Boolean> = _persistentModeEnabled

    private val _nativeBoostEnabled = MutableStateFlow(false)
    val nativeBoostEnabled: StateFlow<Boolean> = _nativeBoostEnabled

    private val _offlineAdbEnabled = MutableStateFlow(false)
    val offlineAdbEnabled: StateFlow<Boolean> = _offlineAdbEnabled

    init {
        loadSettings()
    }

    private fun loadSettings() {
        val settings = PluginPreferences.getPluginSettings()
        _plugins.value = PluginPreferences.getPluginList()
        _persistentModeEnabled.value = settings.persistentMode
        _nativeBoostEnabled.value = settings.nativeBoostEnabled
        _offlineAdbEnabled.value = settings.offlineAdbEnabled
    }

    fun disablePlugin(pluginId: String): Boolean {
        return try {
            PluginPreferences.updatePluginEnabled(pluginId, false)
            loadSettings()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun enablePlugin(pluginId: String): Boolean {
        return try {
            PluginPreferences.updatePluginEnabled(pluginId, true)
            loadSettings()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isPluginEnabled(pluginId: String): Boolean {
        val settings = PluginPreferences.getPluginSettings()
        return settings.enabledPlugins.contains(pluginId)
    }

    fun setPersistentMode(enabled: Boolean): Boolean {
        return try {
            PluginPreferences.setPersistentMode(enabled)
            _persistentModeEnabled.value = enabled
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun setNativeBoost(enabled: Boolean): Boolean {
        return try {
            PluginPreferences.setNativeBoost(enabled)
            _nativeBoostEnabled.value = enabled
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun setOfflineAdb(enabled: Boolean): Boolean {
        return try {
            PluginPreferences.setOfflineAdb(enabled)
            _offlineAdbEnabled.value = enabled
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getLocalConfigDirectory(): String {
        val settings = PluginPreferences.getPluginSettings()
        if (settings.localConfigDir.isEmpty()) {
            val defaultDir = context.filesDir.absolutePath + "/plugin_configs"
            PluginPreferences.setLocalConfigDir(defaultDir)
            return defaultDir
        }
        return settings.localConfigDir
    }

    fun deleteLocalConfigs(): Boolean {
        return try {
            val configDir = getLocalConfigDirectory()
            val file = java.io.File(configDir)
            if (file.exists()) {
                file.deleteRecursively()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun savePluginConfig(config: PluginConfig): Boolean {
        return try {
            val configDir = getLocalConfigDirectory()
            val file = java.io.File(configDir, "${config.id}.json")
            file.parentFile?.mkdirs()
            file.writeText(
                com.google.gson.Gson().toJson(config)
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getPluginConfig(pluginId: String): PluginConfig? {
        return try {
            val configDir = getLocalConfigDirectory()
            val file = java.io.File(configDir, "$pluginId.json")
            if (file.exists()) {
                com.google.gson.Gson().fromJson(
                    file.readText(),
                    PluginConfig::class.java
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun refreshPlugins() {
        loadSettings()
    }
}
