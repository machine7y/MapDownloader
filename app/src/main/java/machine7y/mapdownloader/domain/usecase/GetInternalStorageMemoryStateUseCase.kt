package machine7y.mapdownloader.domain.usecase

import machine7y.mapdownloader.domain.base.usecase.BaseNoParamsUseCase
import machine7y.mapdownloader.domain.entity.Memory
import machine7y.mapdownloader.domain.source.InternalMemorySource
import javax.inject.Inject

class GetInternalStorageMemoryStateUseCase @Inject constructor(
    private val internalMemorySource: InternalMemorySource,
) : BaseNoParamsUseCase<Memory>() {

    override suspend fun execute() = internalMemorySource.getMemory()
}
