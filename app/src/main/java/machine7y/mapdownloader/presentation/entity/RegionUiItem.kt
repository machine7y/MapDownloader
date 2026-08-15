package machine7y.mapdownloader.presentation.entity

sealed interface RegionUiItem {

    val id: Int

    data class ContinentUiItem(
        override val id: Int,
        val name: String,
    ) : RegionUiItem

    data class CountryUiItem(
        override val id: Int,
        val name: String,
    ) : RegionUiItem
}
