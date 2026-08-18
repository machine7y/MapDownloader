package machine7y.mapdownloader.domain.entity.download

data class DownloadItem(
    val fileId: String,
    val status: DownloadItemStatus,
    val attempt: Int = 0,
)

enum class DownloadItemStatus {
    PENDING,
    RUNNING,
    FAILED
}
