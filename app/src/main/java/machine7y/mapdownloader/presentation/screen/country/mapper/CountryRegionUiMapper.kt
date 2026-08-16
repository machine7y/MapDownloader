package machine7y.mapdownloader.presentation.screen.country.mapper

import machine7y.mapdownloader.domain.entity.RegionNode
import machine7y.mapdownloader.presentation.entity.RegionUiItem
import javax.inject.Inject

class CountryRegionUiMapper @Inject constructor() {

    fun map(region: RegionNode): List<RegionUiItem.CountryUiItem> =
        region.children.map { child ->
            RegionUiItem.CountryUiItem(
                localRegionId = child.localRegionId,
                name = child.name,
                isMap = child.isMap,
                hasChildren = child.children.isNotEmpty(),
            )
        }
}
