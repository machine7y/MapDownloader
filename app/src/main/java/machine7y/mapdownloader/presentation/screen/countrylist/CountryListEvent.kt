package machine7y.mapdownloader.presentation.screen.countrylist

import machine7y.mapdownloader.presentation.base.mvvm.BaseEvent

sealed interface CountryListEvent : BaseEvent {

    data class OnCountryClicked(
        val localRegionId: Int,
        val hasChildren: Boolean,
    ) : CountryListEvent
}
