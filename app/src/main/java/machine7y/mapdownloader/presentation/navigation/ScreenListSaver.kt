package machine7y.mapdownloader.presentation.navigation

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.serialization.json.Json
import machine7y.mapdownloader.presentation.screen.Screen

val ScreenListSaver: Saver<SnapshotStateList<Screen>, List<String>> = Saver(
    save = { stack -> stack.map { Json.encodeToString(it) } },
    restore = { saved -> saved.map { Json.decodeFromString<Screen>(it) }.toMutableStateList() },
)
