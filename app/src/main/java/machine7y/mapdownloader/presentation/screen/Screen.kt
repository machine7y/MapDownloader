package machine7y.mapdownloader.presentation.screen

sealed interface Screen {

    data object CountryList: Screen

    data class Country(
        val localRegionId: Int,
    ): Screen
}
