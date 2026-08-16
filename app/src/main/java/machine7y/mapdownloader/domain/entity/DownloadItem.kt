package machine7y.mapdownloader.domain.entity

enum class DownloadItemStatus { PENDING, RUNNING, FAILED }

data class DownloadItem(
    val fileId: String,
    val status: DownloadItemStatus = DownloadItemStatus.PENDING,
    val attempt: Int = 0,
)
