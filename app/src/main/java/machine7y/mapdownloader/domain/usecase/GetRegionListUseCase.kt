package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseNoParamsUseCase
import machine7y.mapdownloader.domain.entity.Region
import machine7y.mapdownloader.domain.source.RegionSource
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(
    private val regionsSource: RegionSource,
) : BaseNoParamsUseCase<List<Region>>() {

    override suspend fun execute() = regionsSource.getRegionList()
}
