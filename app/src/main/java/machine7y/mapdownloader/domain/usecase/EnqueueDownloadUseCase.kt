package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseParamsUseCase
import machine7y.mapdownloader.domain.source.DownloadQueueSource
import javax.inject.Inject

class EnqueueDownloadUseCase @Inject constructor(
    private val downloadQueueSource: DownloadQueueSource,
) : BaseParamsUseCase<EnqueueDownloadUseCaseParam, Unit>() {

    override suspend fun execute(param: EnqueueDownloadUseCaseParam) {
        downloadQueueSource.enqueue(param.fileId)
    }
}

data class EnqueueDownloadUseCaseParam(
    val fileId: String,
)
