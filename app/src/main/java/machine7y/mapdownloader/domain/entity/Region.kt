package machine7y.mapdownloader.domain.entity

sealed interface Region {

    val id: Int

    data class Continent(
        override val id: Int,
        val name: String,
    ) : Region

    data class Country(
        override val id: Int,
        val name: String,
    ) : Region
}
