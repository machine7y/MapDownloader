package machine7y.mapdownloader.presentation.entity

import androidx.annotation.FloatRange

data class MemoryUi(
    val freeGb: Float = 0f,
    @param:FloatRange(from = 0.0, to = 1.0)
    val usedFraction: Float = 0f,
)
