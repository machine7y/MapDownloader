package machine7y.mapdownloader.domain.entity

data class Memory(
    val freeBytes: Long,
    val totalBytes: Long,
    val usedFraction: Float,
)
