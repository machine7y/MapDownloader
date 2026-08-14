package machine7y.mapdownloader.presentation.screen.countrylist

import machine7y.mapdownloader.presentation.base.mvvm.BaseState
import machine7y.mapdownloader.presentation.entity.MemoryUi

data class CountryListState(
    val memory: MemoryUi = MemoryUi(),
) : BaseState
