package machine7y.mapdownloader.domain.usecase

import kotlinx.coroutines.flow.Flow
import machine7y.mapdownloader.domain.base.usecase.BaseParamsFlowUseCase
import machine7y.mapdownloader.domain.entity.download.DownloadState
import machine7y.mapdownloader.domain.source.DownloadQueueSource
import javax.inject.Inject

class ObserveDownloadStatesUseCase @Inject constructor(
    private val downloadQueueSource: DownloadQueueSource,
) : BaseParamsFlowUseCase<ObserveDownloadStatesUseCaseParam, Map<String, DownloadState>>() {

    override suspend fun execute(params: ObserveDownloadStatesUseCaseParam): Flow<Map<String, DownloadState>> =
        downloadQueueSource.observeAll(params.fileIds)
}

data class ObserveDownloadStatesUseCaseParam(
    val fileIds: Set<String>,
)
