package machine7y.mapdownloader.data.remote.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import machine7y.mapdownloader.data.remote.download.DownloadDrainer
import machine7y.mapdownloader.data.remote.download.DownloadFileEngine
import machine7y.mapdownloader.data.remote.download.DownloadProgressBus
import machine7y.mapdownloader.data.remote.download.DownloadQueueStore
import machine7y.mapdownloader.domain.entity.download.DownloadItemStatus
import machine7y.mapdownloader.domain.entity.download.DownloadState
import machine7y.mapdownloader.domain.source.DownloadQueueSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadQueueSourceImpl @Inject constructor(
    private val downloadQueueStore: DownloadQueueStore,
    private val downloadProgressBus: DownloadProgressBus,
    private val downloadFileEngine: DownloadFileEngine,
    private val downloadDrainer: DownloadDrainer,
) : DownloadQueueSource {

    private val removalTickFlow = MutableStateFlow(0)

    override fun enqueue(fileId: String) {
        downloadQueueStore.add(fileId)
        downloadDrainer.ensureRunning()
    }

    override fun remove(fileId: String) {
        downloadQueueStore.remove(fileId)
        downloadFileEngine.deleteTargetFile(fileId)
        removalTickFlow.update { it + 1 }
    }

    override fun observeAll(fileIdSet: Set<String>): Flow<Map<String, DownloadState>> =
        combine(downloadQueueStore.queueFlow, downloadProgressBus.downloadFlow, removalTickFlow) { items, live, _ ->
            fileIdSet.associateWith { fileId ->
                val item = items.firstOrNull { it.fileId == fileId }
                when {
                    item == null -> {
                        if (downloadFileEngine.getTargetFile(fileId).exists()) {
                            DownloadState.Completed
                        } else {
                            DownloadState.Idle
                        }
                    }
                    item.status == DownloadItemStatus.RUNNING -> {
                        live[fileId]?.let {
                            DownloadState.InProgress(it.bytes, it.total.takeIf { total -> total > 0 })
                        } ?: DownloadState.InProgress(0, null)
                    }
                    item.status == DownloadItemStatus.FAILED -> DownloadState.Failed
                    else -> DownloadState.Enqueued
                }
            }
        }.distinctUntilChanged()
}