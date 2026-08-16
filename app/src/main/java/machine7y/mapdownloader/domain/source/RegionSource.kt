package machine7y.mapdownloader.domain.source

import machine7y.mapdownloader.domain.entity.RegionNode

interface RegionSource {

    suspend fun getRegionList(): List<RegionNode>

    suspend fun getRegion(localRegionId: Int): RegionNode
}
