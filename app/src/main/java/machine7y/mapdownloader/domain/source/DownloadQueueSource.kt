package machine7y.mapdownloader.domain.source

import kotlinx.coroutines.flow.Flow
import machine7y.mapdownloader.domain.entity.download.DownloadState

interface DownloadQueueSource {

    fun enqueue(fileId: String)

    fun remove(fileId: String)

    fun observeAll(fileIdSet: Set<String>): Flow<Map<String, DownloadState>>
}
