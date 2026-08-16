package machine7y.mapdownloader.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import machine7y.mapdownloader.presentation.screen.Screen
import machine7y.mapdownloader.presentation.screen.Screen.CountryList
import javax.inject.Inject

class RouterImpl @Inject constructor() : Router {

    private var stack: SnapshotStateList<Screen> = mutableStateListOf(CountryList)

    override val backStack: List<Screen> get() = stack

    override fun attach(backStack: SnapshotStateList<Screen>) {
        stack = backStack
    }

    override fun navigate(screen: Screen) {
        if (stack.lastOrNull() == screen) return

        stack += screen
    }

    override fun replace(screen: Screen) {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)

        stack += screen
    }

    override fun pop(): Boolean {
        if (stack.size <= 1) return false

        stack.removeAt(stack.lastIndex)

        return true
    }

    override fun popTo(screen: Screen, inclusive: Boolean) {
        val index = stack.indexOfLast { it == screen }
        if (index == -1) return
        val target = if (inclusive) index else index + 1

        while (stack.size > target && stack.size > 1) {
            stack.removeAt(stack.lastIndex)
        }
    }

    override fun resetTo(screen: Screen) {
        stack.clear()
        stack += screen
    }
}
