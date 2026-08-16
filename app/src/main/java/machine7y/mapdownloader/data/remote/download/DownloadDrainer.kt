package machine7y.mapdownloader.data.remote.download

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import machine7y.mapdownloader.domain.source.InternalMemorySource
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_ATTEMPTS = 3

@Singleton
class DownloadDrainer @Inject constructor(
    private val downloadQueueStore: DownloadQueueStore,
    private val downloadEngine: DownloadEngine,
    private val downloadProgressBus: DownloadProgressBus,
    private val internalMemorySource: InternalMemorySource,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
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
                downloadEngine.download(item.fileId) { bytes, total -> downloadProgressBus.publish(item.fileId, bytes, total) }
            } finally {
                downloadProgressBus.clear(item.fileId)
            }

            when (result) {
                EngineResult.Success -> {
                    downloadQueueStore.remove(item.fileId)
                }
                EngineResult.Retriable -> {
                    downloadQueueStore.requeue(item.fileId, item.attempt)
                    return
                }
                EngineResult.Failed -> {
                    val next = item.attempt + 1
                    if (next > MAX_ATTEMPTS) downloadQueueStore.markFailed(item.fileId) else downloadQueueStore.requeue(item.fileId, next)
                }
            }
        }
    }
}
