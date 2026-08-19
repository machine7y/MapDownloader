package machine7y.mapdownloader.data.remote.download

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import machine7y.mapdownloader.core.dispatchers.DispatcherProvider
import machine7y.mapdownloader.domain.entity.download.EngineFileResult
import machine7y.mapdownloader.domain.source.InternalMemorySource
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_ATTEMPTS = 3

@Singleton
class DownloadDrainer @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val downloadQueueStore: DownloadQueueStore,
    private val downloadFileEngine: DownloadFileEngine,
    private val downloadProgressBus: DownloadProgressBus,
    private val internalMemorySource: InternalMemorySource,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcherProvider.default)
    private val drainMutex = Mutex()

    fun ensureRunning() {
        scope.launch {
            if (!drainMutex.tryLock()) return@launch

            try {
                drain()
            } finally {
                drainMutex.unlock()
            }
        }
    }

    private suspend fun drain() {
        internalMemorySource.clearCache()

        while (true) {
            val item = downloadQueueStore.takeNext() ?: break

            val result = try {
                downloadFileEngine.download(
                    fileId = item.fileId,
                    onProgress = { bytes, total -> downloadProgressBus.publish(item.fileId, bytes, total) }
                )
            } finally {
                downloadProgressBus.clear(item.fileId)
            }

            when (result) {
                EngineFileResult.Success -> downloadQueueStore.remove(item.fileId)
                EngineFileResult.Failed -> {
                    val newAttempt = item.attempt + 1
                    if (newAttempt >= MAX_ATTEMPTS) {
                        downloadQueueStore.markFailed(item.fileId)
                    } else {
                        downloadQueueStore.requeue(item.fileId, newAttempt)
                    }
                }
            }
        }
    }
}
