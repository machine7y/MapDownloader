package machine7y.mapdownloader.domain.source

import machine7y.mapdownloader.domain.entity.Region

interface RegionSource {

    suspend fun getRegionList(): List<Region>
}
