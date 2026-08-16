package machine7y.mapdownloader.domain.entity

data class RegionNode(
    val name: String,
    val type: String,
    val isMap: Boolean,
    val children: List<RegionNode>,
)
