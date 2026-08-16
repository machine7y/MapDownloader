package machine7y.mapdownloader.data.remote.download

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import machine7y.mapdownloader.domain.entity.DownloadProgress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadProgressBus @Inject constructor() {

    private val _downloadFlow = MutableStateFlow<Map<String, DownloadProgress>>(emptyMap())
    val downloadFlow: StateFlow<Map<String, DownloadProgress>> = _downloadFlow.asStateFlow()

    fun publish(id: String, bytes: Long, total: Long) = _downloadFlow.update {
        it + (id to DownloadProgress(bytes, total))
    }

    fun clear(id: String) = _downloadFlow.update { it - id }
}

