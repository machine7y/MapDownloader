package machine7y.mapdownloader.presentation.entity

sealed interface RegionUiItem {

    val localRegionId: Int

    data class ContinentUiItem(
        override val localRegionId: Int,
        val name: String,
    ) : RegionUiItem

    data class CountryUiItem(
        override val localRegionId: Int,
        val name: String,
        val downloadName: String,
        val isMap: Boolean,
        val hasChildren: Boolean,
    ) : RegionUiItem
}
