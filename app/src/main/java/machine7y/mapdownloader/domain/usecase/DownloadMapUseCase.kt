package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseParamsUseCase
import machine7y.mapdownloader.domain.source.InternalMemorySource
import machine7y.mapdownloader.domain.source.MapDownloadSource
import javax.inject.Inject

class DownloadMapUseCase @Inject constructor(
    private val mapDownloadSource: MapDownloadSource,
    private val internalMemorySource: InternalMemorySource,
) : BaseParamsUseCase<DownloadMapUseCaseParam, Unit>() {

    override suspend fun execute(param: DownloadMapUseCaseParam) {
        internalMemorySource.clearCache()
        mapDownloadSource.downloadMap(param.downloadName)
    }
}

data class DownloadMapUseCaseParam(
    val downloadName: String,
)
