# Enhanced Features Guide

AxManager now includes powerful new features to give you better control over plugins and system functionality, all designed to work on your Android phone without a PC.

## 🚀 New Features Overview

### 1. **Persistent Existence Mode** 🔄
Keep the app running in the background with all plugins maintaining full functionality.

- **What it does**: Prevents the app from being killed by the system
- **Benefits**: Plugins work continuously without interruption
- **How to enable**: Go to Enhanced Features → Toggle "Persistent Existence Mode"
- **Status indicator**: A persistent notification appears when active

### 2. **Plugin Disabler** ⚙️
Disable any plugin and restore normal functionality without removing the entire plugin.

- **What it does**: Removes the plugin's modifications and restores the original state
- **Benefits**: Safely test different plugin combinations
- **How to use**: Go to Enhanced Features → Toggle individual plugins on/off
- **Result**: Changes take effect immediately

### 3. **Login Appliers** 🔌
Select and apply specific plugins from a dedicated screen on the home screen.

- **What it does**: Provides an easy interface to manage which plugin is active
- **Benefits**: Quick plugin switching without multiple taps
- **How to access**: Tap "Login Appliers" button on home screen
- **Note**: Only one plugin can be fully active at a time

### 4. **Plugin Booster (Native Runtime)** ⚡
Makes plugins run natively without needing AxManager to be active.

- **What it does**: Compiles plugins to run independently
- **Benefits**: Reduced battery usage, faster execution
- **Local Config**: Downloads and stores plugin settings locally on your device
- **How to enable**: Go to Enhanced Features → Toggle "Plugin Booster (Native Runtime)"
- **Delete configs**: Button appears to safely remove downloaded configs

### 5. **Offline ADB Connection** [EXPERIMENTAL] 🔌
Use previously established local ADB connections without needing a PC.

- **What it does**: Works with cached ADB connections established before
- **Requirements**: Must have set up ADB connection previously
- **Limitations**: Cannot establish new ADB connections, only uses existing ones
- **How to enable**: Go to Enhanced Features → Toggle "Offline ADB Connection"
- **Warning**: Experimental feature - use with caution

## 📱 Home Screen Features

The home screen now displays quick-access buttons for:

```
┌─────────────┐  ┌─────────────┐
│   🔄 Keep   │  │   ⚙️ Plugin │
│  Persistent │  │  Disabler   │
└─────────────┘  └─────────────┘

┌─────────────┐  ┌─────────────┐
│   ⚡ Native │  │  🔌 Offline │
│  Booster    │  │    ADB      │
└─────────────┘  └─────────────┘
```

## ⚙️ Settings Location

All enhanced features can be configured at:
**Home Screen → Enhanced Features Button**

Or access the detailed settings screen for more options.

## 💾 Data Storage

- All plugin configurations are stored locally on your device
- Settings are saved in `app_cache/plugin_configs/`
- No data is sent to external servers
- You can delete all configs at any time

## ⚠️ Important Notes

1. **Persistent Mode**: Requires FOREGROUND_SERVICE permission. A notification will appear when active.
2. **Native Boost**: Experimental - may require reboot to take full effect
3. **Offline ADB**: Must have working ADB setup before enabling
4. **Data Safety**: Always backup important configs before deleting

## 🆘 Troubleshooting

### Features not working?
- Check if app has all required permissions
- Try disabling and re-enabling the feature
- Clear app cache: Settings → Apps → AxManager → Storage → Clear Cache

### Lost changes?
- All settings are auto-saved to local storage
- Check the "Local Config" folder for backup files

### Need to restart?
- Exit AxManager completely
- Clear it from recent apps
- Reopen the app

## 📝 Version Info

- First released with these enhanced features in v1.5+
- Check GitHub releases for latest updates
- Report issues at: https://github.com/Notvro33/AxManager/issues
