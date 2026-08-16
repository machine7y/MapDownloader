package machine7y.mapdownloader.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import machine7y.mapdownloader.presentation.screen.Screen.Country
import machine7y.mapdownloader.presentation.screen.Screen.CountryList
import machine7y.mapdownloader.presentation.screen.country.CountryScreen
import machine7y.mapdownloader.presentation.screen.countrylist.CountryListScreen

@Composable
fun MainScreen() {
    val backStack = remember { mutableStateListOf<Screen>(CountryList) }
    val onBackClicked: () -> Unit = { backStack.removeLastOrNull() }

    NavDisplay(
        backStack = backStack,
        onBack = onBackClicked,
        entryProvider = entryProvider {
            entry<CountryList> {
                CountryListScreen(
                    onCountryClicked = { localRegionId ->
                        backStack.add(Country(localRegionId))
                    },
                )
            }
            entry<Country> { key ->
                CountryScreen(
                    localRegionId = key.localRegionId,
                    onBackClicked = onBackClicked,
                )
            }
        }
    )
}
