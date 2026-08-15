package machine7y.mapdownloader.presentation.screen.countrylist.mapper

import machine7y.mapdownloader.domain.entity.Region
import machine7y.mapdownloader.presentation.entity.RegionUiItem
import javax.inject.Inject

class RegionUiMapper @Inject constructor() {

    fun map(regionList: List<Region>): List<RegionUiItem> = regionList.map(::map)

    private fun map(region: Region): RegionUiItem = when (region) {
        is Region.Continent -> RegionUiItem.ContinentUiItem(
            id = region.id,
            name = region.name,
        )
        is Region.Country -> RegionUiItem.CountryUiItem(
            id = region.id,
            name = region.name,
        )
    }
}
