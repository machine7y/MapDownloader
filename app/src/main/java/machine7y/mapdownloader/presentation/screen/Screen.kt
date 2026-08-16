package machine7y.mapdownloader.presentation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object CountryList: Screen

    @Serializable
    data class Country(
        val localRegionId: Int,
    ): Screen
}
