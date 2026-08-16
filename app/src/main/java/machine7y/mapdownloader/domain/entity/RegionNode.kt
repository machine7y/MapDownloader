package machine7y.mapdownloader.domain.entity

data class RegionNode(
    val localRegionId: Int,
    val name: String,
    val type: String,
    val isMap: Boolean,
    val downloadName: String,
    val children: List<RegionNode>,
)
