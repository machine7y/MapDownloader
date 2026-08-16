package machine7y.mapdownloader.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import machine7y.mapdownloader.presentation.navigation.Router
import machine7y.mapdownloader.presentation.navigation.ScreenListSaver
import machine7y.mapdownloader.presentation.screen.Screen.Country
import machine7y.mapdownloader.presentation.screen.Screen.CountryList
import machine7y.mapdownloader.presentation.screen.country.CountryScreen
import machine7y.mapdownloader.presentation.screen.countrylist.CountryListScreen

@Composable
fun MainScreen(router: Router) {
    val backStack = rememberSaveable(saver = ScreenListSaver) { mutableStateListOf(CountryList) }
    router.attach(backStack)

    BackHandler(enabled = backStack.size > 1) { router.pop() }

    NavDisplay(
        backStack = backStack,
        onBack = { router.pop() },
        entryProvider = entryProvider {
            entry<CountryList> {
                CountryListScreen()
            }
            entry<Country> { key ->
                CountryScreen(localRegionId = key.localRegionId)
            }
        }
    )
}
