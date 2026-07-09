package frb.axeron.manager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import frb.axeron.manager.data.PluginConfig
import frb.axeron.manager.data.PluginPreferences
import frb.axeron.manager.features.PluginManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EnhancedPluginViewModel : ViewModel() {

    private lateinit var pluginManager: PluginManager

    private val _uiState = MutableStateFlow<PluginUiState>(PluginUiState.Loading)
    val uiState: StateFlow<PluginUiState> = _uiState

    private val _persistentMode = MutableStateFlow(false)
    val persistentMode: StateFlow<Boolean> = _persistentMode

    private val _nativeBoost = MutableStateFlow(false)
    val nativeBoost: StateFlow<Boolean> = _nativeBoost

    private val _offlineAdb = MutableStateFlow(false)
    val offlineAdb: StateFlow<Boolean> = _offlineAdb

    fun initialize(manager: PluginManager) {
        pluginManager = manager
        loadInitialState()
    }

    private fun loadInitialState() {
        viewModelScope.launch {
            try {
                val settings = PluginPreferences.getPluginSettings()
                _persistentMode.value = settings.persistentMode
                _nativeBoost.value = settings.nativeBoostEnabled
                _offlineAdb.value = settings.offlineAdbEnabled
                _uiState.value = PluginUiState.Success(
                    plugins = PluginPreferences.getPluginList(),
                    persistentMode = settings.persistentMode,
                    nativeBoost = settings.nativeBoostEnabled,
                    offlineAdb = settings.offlineAdbEnabled
                )
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun togglePersistentMode(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val success = pluginManager.setPersistentMode(enabled)
                if (success) {
                    _persistentMode.value = enabled
                    updateUiState()
                }
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Failed to toggle persistent mode")
            }
        }
    }

    fun toggleNativeBoost(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val success = pluginManager.setNativeBoost(enabled)
                if (success) {
                    _nativeBoost.value = enabled
                    updateUiState()
                }
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Failed to toggle native boost")
            }
        }
    }

    fun toggleOfflineAdb(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val success = pluginManager.setOfflineAdb(enabled)
                if (success) {
                    _offlineAdb.value = enabled
                    updateUiState()
                }
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Failed to toggle offline ADB")
            }
        }
    }

    fun deleteLocalConfigs() {
        viewModelScope.launch {
            try {
                val success = pluginManager.deleteLocalConfigs()
                if (success) {
                    _uiState.value = PluginUiState.Success(
                        message = "Local configs deleted successfully",
                        plugins = PluginPreferences.getPluginList()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Failed to delete configs")
            }
        }
    }

    fun disablePlugin(pluginId: String) {
        viewModelScope.launch {
            try {
                pluginManager.disablePlugin(pluginId)
                updateUiState()
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Failed to disable plugin")
            }
        }
    }

    private fun updateUiState() {
        viewModelScope.launch {
            try {
                val settings = PluginPreferences.getPluginSettings()
                _uiState.value = PluginUiState.Success(
                    plugins = PluginPreferences.getPluginList(),
                    persistentMode = settings.persistentMode,
                    nativeBoost = settings.nativeBoostEnabled,
                    offlineAdb = settings.offlineAdbEnabled
                )
            } catch (e: Exception) {
                _uiState.value = PluginUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class PluginUiState {
    object Loading : PluginUiState()
    data class Success(
        val plugins: List<PluginConfig> = emptyList(),
        val persistentMode: Boolean = false,
        val nativeBoost: Boolean = false,
        val offlineAdb: Boolean = false,
        val message: String = ""
    ) : PluginUiState()
    data class Error(val message: String) : PluginUiState()
}
