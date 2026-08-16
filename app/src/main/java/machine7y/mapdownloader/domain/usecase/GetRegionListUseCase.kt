package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseNoParamsUseCase
import machine7y.mapdownloader.domain.entity.RegionNode
import machine7y.mapdownloader.domain.source.RegionSource
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(
    private val regionsSource: RegionSource,
) : BaseNoParamsUseCase<List<RegionNode>>() {

    override suspend fun execute() = regionsSource.getRegionList()
}
