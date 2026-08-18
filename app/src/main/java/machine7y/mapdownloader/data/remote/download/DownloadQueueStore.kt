package machine7y.mapdownloader.data.remote.download

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import machine7y.mapdownloader.domain.entity.download.DownloadItem
import machine7y.mapdownloader.domain.entity.download.DownloadItemStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadQueueStore @Inject constructor() {

    private val _queueFlow = MutableStateFlow<List<DownloadItem>>(emptyList())
    val queueFlow: StateFlow<List<DownloadItem>> = _queueFlow.asStateFlow()

    fun add(fileId: String) {
        _queueFlow.update { items ->
            val existing = items.firstOrNull { it.fileId == fileId }
            when {
                existing == null -> {
                    items +
                        DownloadItem(
                            fileId = fileId,
                            status = DownloadItemStatus.PENDING,
                        )
                }
                existing.status == DownloadItemStatus.FAILED -> {
                    items.map {
                        if (it.fileId == fileId) {
                            DownloadItem(
                                fileId = fileId,
                                status = DownloadItemStatus.PENDING,
                            )
                        } else {
                            it
                        }
                    }
                }
                else -> items
            }
        }
    }

    fun takeNext(): DownloadItem? {
        var nextItem: DownloadItem? = null

        _queueFlow.update { items ->
            val nextDownloadItem = items.firstOrNull { it.status == DownloadItemStatus.PENDING } ?: return@update items
            val runningDownloadItem = nextDownloadItem.copy(status = DownloadItemStatus.RUNNING)

            nextItem = runningDownloadItem

            items.map {
                if (it.fileId == nextDownloadItem.fileId) {
                    runningDownloadItem
                } else {
                    it
                }
            }
        }

        return nextItem
    }

    fun remove(fileId: String) = _queueFlow.update { items ->
        items.filter { it.fileId != fileId }
    }

    fun requeue(fileId: String, attempt: Int) = update(fileId) {
        it.copy(
            status = DownloadItemStatus.PENDING,
            attempt = attempt,
        )
    }

    fun markFailed(fileId: String) = update(fileId) {
        it.copy(status = DownloadItemStatus.FAILED)
    }

    private fun update(fileId: String, block: (DownloadItem) -> DownloadItem) = _queueFlow.update { items ->
        items.map {
            if (it.fileId == fileId) {
                block(it)
            } else {
                it
            }
        }
    }
}
