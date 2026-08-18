package machine7y.mapdownloader.domain.entity.download

sealed interface DownloadState {

    data object Idle : DownloadState

    data object Enqueued : DownloadState

    data class InProgress(
        val bytesDownloaded: Long,
        val totalBytes: Long?,
    ) : DownloadState {

        val fraction: Float? = totalBytes
            ?.takeIf { it > 0 }
            ?.let { (bytesDownloaded.toFloat() / it).coerceIn(0f, 1f) }
    }

    data object Completed : DownloadState

    data object Failed : DownloadState
}
