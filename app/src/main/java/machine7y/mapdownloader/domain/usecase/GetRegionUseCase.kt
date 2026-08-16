package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseParamsUseCase
import machine7y.mapdownloader.domain.entity.RegionNode
import machine7y.mapdownloader.domain.source.RegionSource
import javax.inject.Inject

class GetRegionUseCase @Inject constructor(
    private val regionsSource: RegionSource,
) : BaseParamsUseCase<RegionUseCaseParam, RegionNode>() {

    override suspend fun execute(param: RegionUseCaseParam): RegionNode {
        return regionsSource.getRegion(param.localRegionId)
    }
}

data class RegionUseCaseParam(
    val localRegionId: Int,
)
