package machine7y.mapdownloader.presentation.screen.countrylist.mapper

import machine7y.mapdownloader.domain.entity.RegionNode
import machine7y.mapdownloader.presentation.entity.RegionUiItem
import machine7y.mapdownloader.presentation.entity.RegionUiItem.ContinentUiItem
import machine7y.mapdownloader.presentation.entity.RegionUiItem.CountryUiItem
import javax.inject.Inject

private const val TYPE_CONTINENT = "continent"

private const val DEPTH_CONTINENT = 0
private const val DEPTH_COUNTRY = 1

class RegionUiMapper @Inject constructor() {

    fun map(regionList: List<RegionNode>): List<RegionUiItem> {
        val items = mutableListOf<RegionUiItem>()
        flatten(regionList, depth = DEPTH_CONTINENT, items = items)

        return items
    }

    private fun flatten(nodes: List<RegionNode>, depth: Int, items: MutableList<RegionUiItem>) {
        for (node in nodes) {
            val name = node.name
            if (name.isNotEmpty()) {
                when {
                    depth == DEPTH_CONTINENT && node.type == TYPE_CONTINENT -> items.add(
                        ContinentUiItem(
                            localRegionId = node.localRegionId,
                            name = name,
                        )
                    )
                    depth == DEPTH_COUNTRY -> items.add(
                        CountryUiItem(
                            localRegionId = node.localRegionId,
                            name = name,
                            downloadName = node.downloadName,
                            isMap = node.isMap,
                            hasChildren = node.children.isNotEmpty(),
                        )
                    )
                }
            }
            flatten(node.children, depth + 1, items)
        }
    }
}
