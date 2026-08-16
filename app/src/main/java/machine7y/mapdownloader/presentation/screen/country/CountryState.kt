package machine7y.mapdownloader.presentation.screen.country

import machine7y.mapdownloader.presentation.base.mvvm.BaseState
import machine7y.mapdownloader.presentation.entity.RegionUiItem

data class CountryState(
    val name: String = "",
    val regionList: List<RegionUiItem.CountryUiItem> = emptyList(),
) : BaseState
