package frb.axeron.manager.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeatureButton(
    title: String,
    description: String,
    icon: String,
    onClick: () -> Unit,
    isActive: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun EnhancedFeaturesButtonRow(
    onPersistentModeClick: () -> Unit,
    onPluginDisablerClick: () -> Unit,
    onNativeBoostClick: () -> Unit,
    onOfflineAdbClick: () -> Unit,
    persistentModeActive: Boolean = false,
    nativeBoostActive: Boolean = false,
    offlineAdbActive: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Enhanced Features",
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeatureButton(
                    title = "Persistent",
                    description = "Keep Alive",
                    icon = "🔄",
                    onClick = onPersistentModeClick,
                    isActive = persistentModeActive,
                    modifier = Modifier.weight(1f)
                )
                FeatureButton(
                    title = "Disabler",
                    description = "Plugins",
                    icon = "⚙️",
                    onClick = onPluginDisablerClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeatureButton(
                    title = "Booster",
                    description = "Native Mode",
                    icon = "⚡",
                    onClick = onNativeBoostClick,
                    isActive = nativeBoostActive,
                    modifier = Modifier.weight(1f)
                )
                FeatureButton(
                    title = "Offline ADB",
                    description = "Experimental",
                    icon = "🔌",
                    onClick = onOfflineAdbClick,
                    isActive = offlineAdbActive,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
