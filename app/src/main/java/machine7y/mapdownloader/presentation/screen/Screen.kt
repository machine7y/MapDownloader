package machine7y.mapdownloader.presentation.screen

sealed interface Screen {

    data object CountryList: Screen

    data object Country: Screen
}
