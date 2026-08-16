package machine7y.mapdownloader.domain.source

import machine7y.mapdownloader.domain.entity.Memory

interface InternalMemorySource {

    suspend fun getMemory(): Memory

    suspend fun clearCache()
}
