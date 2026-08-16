package machine7y.mapdownloader.presentation.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import machine7y.mapdownloader.presentation.screen.Screen

interface Router {

    val backStack: List<Screen>

    fun attach(backStack: SnapshotStateList<Screen>)

    fun navigate(screen: Screen)

    fun replace(screen: Screen)

    fun pop(): Boolean

    fun popTo(screen: Screen, inclusive: Boolean = false)

    fun resetTo(screen: Screen)
}
