package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseParamsUseCase
import machine7y.mapdownloader.domain.source.DownloadQueueSource
import javax.inject.Inject

class RemoveDownloadUseCase @Inject constructor(
    private val downloadQueueSource: DownloadQueueSource,
) : BaseParamsUseCase<RemoveDownloadUseCaseParam, Unit>() {

    override suspend fun execute(param: RemoveDownloadUseCaseParam) {
        downloadQueueSource.remove(param.fileId)
    }
}

data class RemoveDownloadUseCaseParam(
    val fileId: String,
)
