package machine7y.mapdownloader.presentation.screen.countrylist

import machine7y.mapdownloader.presentation.base.mvvm.BaseState
import machine7y.mapdownloader.presentation.entity.MemoryUi
import machine7y.mapdownloader.presentation.entity.RegionUiItem

data class CountryListState(
    val memory: MemoryUi = MemoryUi(),
    val regionList: List<RegionUiItem> = emptyList(),
) : BaseState
