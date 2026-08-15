package machine7y.mapdownloader.presentation.screen.countrylist.mapper

import machine7y.mapdownloader.domain.entity.Memory
import machine7y.mapdownloader.presentation.entity.MemoryUi
import javax.inject.Inject

private const val BYTES_IN_GB = 1024f * 1024f * 1024f

class MemoryUiMapper @Inject constructor() {

    fun map(memory: Memory) = MemoryUi(
        freeGb = memory.freeBytes / BYTES_IN_GB,
        usedFraction = memory.usedFraction,
    )
}
